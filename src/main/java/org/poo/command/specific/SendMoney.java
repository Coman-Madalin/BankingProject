package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.account.Account;
import org.poo.command.BaseCommand;
import org.poo.commerciant.CashbackPlans;
import org.poo.commerciant.Commerciant;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.transactions.specific.TransferTransaction;
import org.poo.user.User;

import static org.poo.input.Input.isAlias;

/**
 * The type Send money.
 */
public final class SendMoney extends BaseCommand {
    private String account;
    private String receiver;
    private double amount;
    private String email;
    private String description;

    /**
     * Instantiates a new Send money.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public SendMoney(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        Input input = Input.getInstance();
        // TODO: Maybe check first time for user using email and then on it check for account
        final User senderUser = input.getUsers().getUserByEmail(email);

        if (getTimestamp() == 13) {
            System.out.println("dada");
        }

        if (senderUser == null) {
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("timestamp", getTimestamp());
            outputJson.addProperty("description", "User not found");
            setOutput(outputJson.toString());
            return;
        }

        final Account senderAccount = input.getUsers().getAccountByEmailAndIBAN(email, account);

        Commerciant commerciant = Input.getInstance().getCommerciants()
                .getCommerciantByIBAN(receiver);

        if (commerciant != null) {
            System.out.println("SEND MONEY: FOUND COMMERCIANT");
            sendToCompany(senderAccount, commerciant);
            return;
        }

        final User receiverUser = input.getUsers().getUserByIBAN(receiver);

        if (receiverUser == null) {
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("timestamp", getTimestamp());
            outputJson.addProperty("description", "User not found");
            setOutput(outputJson.toString());
            return;
        }

        final Account receiverAccount = input.getUsers().getAccountByIBAN(receiver);

        if (senderAccount == null || receiverAccount == null) {
            return;
        }

        if (isAlias(account) && !isAlias(receiver)) {
            return;
        }

        if (isAlias(account) || isAlias(receiver)) {
            if (!senderUser.equals(receiverUser)) {
                return;
            }
        }


        double amountInRON = Input.getInstance().getExchanges().convertCurrency(amount,
                senderAccount.getCurrency(), "RON");
        double commissionInRON = senderUser.getServicePlan().getCommission(amountInRON);
        double senderCurrencyCommission = input.getExchanges().convertCurrency(commissionInRON, "RON",
                senderAccount.getCurrency());
        double senderTotal = amount + senderCurrencyCommission;

        if (!senderAccount.hasEnoughBalance(senderTotal)) {
            senderAccount.getTransactionsHistory().add(new BaseTransaction(
                    getTimestamp()
            ));
            return;
        }

        senderAccount.decreaseBalance(senderTotal);

        final double receiverCurrencyAmount = input.getExchanges().convertCurrency(amount,
                senderAccount.getCurrency(), receiverAccount.getCurrency());

        receiverAccount.increaseBalance(receiverCurrencyAmount);

        senderAccount.getTransactionsHistory().add(new TransferTransaction(
                description,
                getTimestamp(),
                account,
                receiver,
                String.format("%.1f %s", amount, senderAccount.getCurrency()),
                "sent"
        ));

        receiverAccount.getTransactionsHistory().add(new TransferTransaction(
                description,
                getTimestamp(),
                account,
                receiver,
                String.format("%.1f %s", receiverCurrencyAmount, receiverAccount.getCurrency()),
                "received"
        ));

    }

    private void sendToCompany(Account account, Commerciant commerciant) {
        // TODO: Might need to check after applying discounts
        if (!account.hasEnoughBalance(amount)) {
            account.getTransactionsHistory().add(new BaseTransaction(getTimestamp()));
            return;
        }

        User user = account.getUser();
        double totalAmount = amount;
        // TODO: Convert amount in RON

        double senderCommission = user.getServicePlan().getCommission(amount);
        totalAmount += senderCommission;
        account.decreaseBalance(senderCommission);

        double discount = account.getDiscountForTransactionCount(commerciant.getType());
        if (discount != 0) {
            totalAmount = totalAmount - amount * discount;
            account.invalidateCashback();
        }

        double amountInRON = Input.getInstance().getExchanges().convertCurrency(amount,
                account.getCurrency(), "RON");

        if (commerciant.getCashback() == CashbackPlans.SPENDING_THRESHOLD) {
            discount = account.getSpendingDiscount(commerciant, amountInRON);
            if (discount != 0) {
                totalAmount = totalAmount - amount * discount;
            }
        }

        account.addTransaction(commerciant, amountInRON);

        if (commerciant.getCashback() == CashbackPlans.NR_OF_TRANSACTIONS) {
            account.updateCashback(commerciant);
        }

        account.decreaseBalance(amount);
    }
}
