package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.account.BaseAccount;
import org.poo.command.BaseCommand;
import org.poo.commerciant.CashbackPlans;
import org.poo.commerciant.Commerciant;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.transactions.specific.PlanUpgradeTransaction;
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
        final Input input = Input.getInstance();
        final User senderUser = input.getUsers().getUserByEmail(email);

        if (senderUser == null) {
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("timestamp", getTimestamp());
            outputJson.addProperty("description", "User not found");
            setOutput(outputJson.toString());
            return;
        }

        final BaseAccount senderAccount = senderUser.getAccountByIBAN(account);

        final Commerciant commerciant = Input.getInstance().getCommerciants()
                .getCommerciantByIBAN(receiver);

        if (commerciant != null) {
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

        final BaseAccount receiverAccount = input.getUsers().getAccountByIBAN(receiver);

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


        final double amountInRON = Input.getInstance().getExchanges().convertCurrency(amount,
                senderAccount.getCurrency(), "RON");
        final double commissionInRON = senderUser.getServicePlan().getCommission(amountInRON);
        final double senderCurrencyCommission = input.getExchanges()
                .convertCurrency(commissionInRON, "RON", senderAccount.getCurrency());
        final double senderTotal = amount + senderCurrencyCommission;

        if (!senderAccount.hasEnoughBalance(senderTotal)) {
            senderAccount.getTransactionsHistory().add(new BaseTransaction(getTimestamp()));
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
                receiverCurrencyAmount + " " + receiverAccount.getCurrency(),
                "received"
        ));
    }

    private void sendToCompany(final BaseAccount baseAccount, final Commerciant commerciant) {
        final User user = baseAccount.getUser();
        double totalAmount = amount;
        final double amountInRON = Input.getInstance().getExchanges().convertCurrency(amount,
                baseAccount.getCurrency(), "RON");

        final double senderCommissionInRON = user.getServicePlan().getCommission(amountInRON);
        final double senderCommission = Input.getInstance().getExchanges().convertCurrency(
                senderCommissionInRON, "RON", baseAccount.getCurrency());
        totalAmount += senderCommission;

        double discount = baseAccount.getDiscountForTransactionCount(commerciant.getType());
        if (discount != 0) {
            totalAmount = totalAmount - amount * discount;
            baseAccount.invalidateCashback();
        }

        if (commerciant.getCashback() == CashbackPlans.SPENDING_THRESHOLD) {
            discount = baseAccount.getSpendingDiscount(amountInRON);
            if (discount != 0) {
                totalAmount = totalAmount - amount * discount;
            }
        }

        baseAccount.addTransaction(commerciant, amountInRON);

        if (commerciant.getCashback() == CashbackPlans.NR_OF_TRANSACTIONS) {
            baseAccount.updateCashback(commerciant);
        }

        if (!baseAccount.hasEnoughBalance(amount)) {
            baseAccount.getTransactionsHistory().add(new BaseTransaction(getTimestamp()));
            return;
        }

        baseAccount.decreaseBalance(totalAmount);

        baseAccount.getTransactionsHistory().add(new TransferTransaction(
                description,
                getTimestamp(),
                account,
                receiver,
                String.format("%.1f %s", amount, baseAccount.getCurrency()),
                "sent"
        ));

        final boolean result = user.increaseNumberOfOver300Payments(amountInRON);

        if (result) {
            baseAccount.getTransactionsHistory().add(new PlanUpgradeTransaction(
                    "Upgrade plan", getTimestamp(), "gold", account
            ));
        }
    }
}
