package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.command.BaseCommand;
import org.poo.commerciant.CashbackPlans;
import org.poo.commerciant.Commerciant;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.transactions.specific.PaymentTransaction;
import org.poo.user.Account;
import org.poo.user.Card;
import org.poo.user.User;

/**
 * The type Pay online.
 */
public final class PayOnline extends BaseCommand {
    private String cardNumber;
    private double amount;
    private String currency;
    private String description;
    private String commerciant;
    private String email;

    /**
     * Instantiates a new Pay online.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public PayOnline(final String command, final int timestamp) {
        super(command, timestamp);
    }

    private void setOutputAsError() {
        final JsonObject outputJson = new JsonObject();
        outputJson.addProperty("description", "Card not found");
        outputJson.addProperty("timestamp", this.getTimestamp());
        this.setOutput(outputJson.toString());
    }

    @Override
    public void execute() {
        final Input input = Input.getInstance();
        final Account account = input.getUsers().getAccountByEmailAndCardNumber(email, cardNumber);

        if (account == null) {
            setOutputAsError();
            return;
        }

        final Card card = account.getCardByCardNumber(cardNumber);

        if (card == null) {
            setOutputAsError();
            return;
        }

        if (card.getStatus().equals("frozen")) {
            account.getTransactionsHistory().add(new BaseTransaction("The card is frozen",
                    getTimestamp()));
            return;
        }

        final User user = account.getUser();

        // TODO: Convert amount in RON
        final double sameCurrencyAmount = input.getExchanges().convertCurrency(amount,
                currency, account.getCurrency());

        double amountInRON = input.getExchanges().convertCurrency(amount, currency, "RON");
        double commissionInRON = user.getServicePlan().getCommission(amountInRON);
        double commission = input.getExchanges().convertCurrency(commissionInRON, "RON", account.getCurrency());

        double totalAmount = sameCurrencyAmount + commission;

        if (!account.hasEnoughBalance(totalAmount)) {
            account.getTransactionsHistory().add(new BaseTransaction(
                    getTimestamp()
            ));
            return;
        }

        Commerciant commerciant1 = Input.getInstance().getCommerciants()
                .getCommerciantByName(commerciant);

        if (commerciant1 == null) {
            System.out.println("NO COMMERCIANT");
            // TODO: there is no commerciant with this name
            return;
        }

        double discount = account.getDiscountForTransactionCount(commerciant1.getType());
        if (discount != 0) {
            totalAmount = totalAmount - sameCurrencyAmount * discount;
            account.invalidateCashback();
        }

        if (commerciant1.getCashback() == CashbackPlans.SPENDING_THRESHOLD) {
            discount = account.getSpendingDiscount(commerciant1, amountInRON);
            if (discount != 0) {
                totalAmount = totalAmount - sameCurrencyAmount * discount;
            }
        }

        account.addTransaction(commerciant1, amountInRON);

        if (commerciant1.getCashback() == CashbackPlans.NR_OF_TRANSACTIONS) {
            account.updateCashback(commerciant1);
        }

        account.getTransactionsHistory().add(new PaymentTransaction(
                "Card payment",
                getTimestamp(),
                sameCurrencyAmount,
                commerciant
        ));

        account.decreaseBalance(totalAmount);

        if (card.isOneTimeCard()) {
            new DeleteCard("deleteCard", getTimestamp(), email, cardNumber).execute();
            new CreateOneTimeCard("createOneTimeCard", getTimestamp(), account.getIban(), email)
                    .execute();
        }

    }
}
