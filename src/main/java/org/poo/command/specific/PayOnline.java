package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.account.BaseAccount;
import org.poo.account.specific.BusinessAccount;
import org.poo.business.Employee;
import org.poo.command.BaseCommand;
import org.poo.commerciant.CashbackPlans;
import org.poo.commerciant.Commerciant;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.transactions.specific.PaymentTransaction;
import org.poo.transactions.specific.PlanUpgradeTransaction;
import org.poo.user.Card;
import org.poo.user.User;

import static org.poo.input.Input.printLog;

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

    private double amountInRON;
    private double amountInAccountCurrency;

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

    private boolean handleBusinessAccount(final BusinessAccount businessAccount,
                                          final double discount) {
        final Employee employee = businessAccount.getEmployeeByEmailAndRole(email, null);
        if (employee == null) {
            setOutputAsError();
            return false;
        }

        final double commissionInRON = businessAccount.getUser().getServicePlan()
                .getCommission(amountInRON);
        final double commission = Input.getInstance().getExchanges()
                .convertCurrency(commissionInRON, "RON", businessAccount.getCurrency());

        final double totalAmount = amountInAccountCurrency + commission - discount;

        if (!businessAccount.hasEnoughBalance(totalAmount)) {
            businessAccount.getTransactionsHistory().add(new BaseTransaction(getTimestamp()));
            return false;
        }

        if (employee.getRole().equals("employee")
                && totalAmount > businessAccount.getSpendingLimit()) {
            return false;
        }

        businessAccount.decreaseBalance(totalAmount);

        employee.addSpending(commerciant, amountInAccountCurrency, getTimestamp());

        printLog("Payonline:business", getTimestamp(), totalAmount, businessAccount.getBalance(),
                businessAccount.getIban());

        return true;
    }

    private boolean handleClassicAccount(final BaseAccount account, final double discount) {
        final Card card = account.getCardByCardNumber(cardNumber);

        if (card == null) {
            setOutputAsError();
            return false;
        }

        if (card.getStatus().equals("frozen")) {
            account.getTransactionsHistory().add(new BaseTransaction("The card is frozen",
                    getTimestamp()));
            return false;
        }

        final User user = account.getUser();

        final double commissionInRON = user.getServicePlan().getCommission(amountInRON);
        final double commission = Input.getInstance().getExchanges()
                .convertCurrency(commissionInRON, "RON", account.getCurrency());

        final double totalAmount = amountInAccountCurrency + commission - discount;

        if (!account.hasEnoughBalance(totalAmount)) {
            account.getTransactionsHistory().add(new BaseTransaction(getTimestamp()));
            return false;
        }

        account.decreaseBalance(totalAmount);

//        if (amountInRON > 300) {
//            boolean result = account.getUser().increaseNumberOfOver300Payments();
//
//            if (result) {
//                account.getTransactionsHistory().add(new PlanUpgradeTransaction(
//                        "Upgrade plan", getTimestamp(), "gold", account.getIban()
//                ));
//            }
//        }

        printLog("PayOnline:classic", getTimestamp(), totalAmount, account.getBalance(),
                account.getIban());

        return true;
    }

    private boolean handleCommerciantReceiver(final BaseAccount account) {
        final Commerciant commerciant1 = Input.getInstance().getCommerciants()
                .getCommerciantByName(commerciant);

        account.addTransaction(commerciant1, amountInRON);

        if (commerciant1.getCashback() == CashbackPlans.NR_OF_TRANSACTIONS) {
            account.updateCashback(commerciant1);
        }

        account.getTransactionsHistory().add(new PaymentTransaction(
                "Card payment",
                getTimestamp(),
                amountInAccountCurrency,
                commerciant
        ));

        final boolean result = account.getUser().increaseNumberOfOver300Payments(amountInRON);
        if (result) {
            account.getTransactionsHistory().add(new PlanUpgradeTransaction(
                    "Upgrade plan", getTimestamp(), "gold", account.getIban()
            ));
        }

        return true;
    }

    /**
     * Gets total discount in ron.
     *
     * @param account the account
     * @return the total discount in ron
     */
    public double getTotalDiscountInRON(final BaseAccount account) {
        double totalDiscount = 0;
        final Commerciant commerciant1 = Input.getInstance().getCommerciants()
                .getCommerciantByName(commerciant);
        amountInAccountCurrency = Input.getInstance().getExchanges()
                .convertCurrency(amount, currency, account.getCurrency());
        amountInRON = Input.getInstance().getExchanges()
                .convertCurrency(amount, currency, "RON");

        double discount = account.getDiscountForTransactionCount(commerciant1.getType());
        if (discount != 0) {
            totalDiscount = totalDiscount + amountInAccountCurrency * discount;
            account.invalidateCashback();
        }

        if (commerciant1.getCashback() == CashbackPlans.SPENDING_THRESHOLD) {
            discount = account.getSpendingDiscount(amountInRON);
            if (discount != 0) {
                totalDiscount = totalDiscount + amountInAccountCurrency * discount;
            }
        }

        return totalDiscount;
    }

    @Override
    public void execute() {
        if (amount == 0) {
            return;
        }

        final Input input = Input.getInstance();
        final BaseAccount account = input.getUsers().getAccountByCardNumber(cardNumber);

        if (account == null) {
            setOutputAsError();
            return;
        }

        if (!account.isValidEmail(email)) {
            setOutputAsError();
            return;
        }

        boolean success;
        final double discount = getTotalDiscountInRON(account);

        if (account.getType().equals("business")) {
            success = handleBusinessAccount((BusinessAccount) account, discount);
            if (!success) {
                return;
            }
        } else {
            success = handleClassicAccount(account, discount);
            if (!success) {
                return;
            }
        }

        success = handleCommerciantReceiver(account);
        if (!success) {
            return;
        }
        final Card card = account.getCardByCardNumber(cardNumber);

        if (card.isOneTimeCard()) {
            new DeleteCard(getTimestamp(), email, cardNumber).forceExecute();
            new CreateOneTimeCard(getTimestamp(), account.getIban(), email).execute();
        }

    }
}
