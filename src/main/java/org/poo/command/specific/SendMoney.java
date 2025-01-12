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
import static org.poo.input.Input.printLog;

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

        if (getTimestamp() == 248) {
            System.out.println("DADAD");
        }

        if (senderUser == null) {
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("timestamp", getTimestamp());
            outputJson.addProperty("description", "User not found");
            setOutput(outputJson.toString());
            return;
        }

        final BaseAccount senderAccount = input.getUsers().getAccountByEmailAndIBAN(email, account);

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


        double amountInRON = Input.getInstance().getExchanges().convertCurrency(amount,
                senderAccount.getCurrency(), "RON");
        double commissionInRON = senderUser.getServicePlan().getCommission(amountInRON);
        double senderCurrencyCommission = input.getExchanges().convertCurrency(commissionInRON, "RON",
                senderAccount.getCurrency());
        double senderTotal = amount + senderCurrencyCommission;

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
                String.format("%.1f %s", receiverCurrencyAmount, receiverAccount.getCurrency()),
                "received"
        ));

        printLog("SendMoney:Classic, FROM:", getTimestamp(), senderTotal,
                senderAccount.getBalance(), senderAccount.getIban());
        printLog("SendMoney:Classic, TO:", getTimestamp(), receiverCurrencyAmount,
                receiverAccount.getBalance(), receiverAccount.getIban());
    }

    private void sendToCompany(BaseAccount baseAccount, Commerciant commerciant) {
        User user = baseAccount.getUser();
        double totalAmount = amount;
        double senderCommission = user.getServicePlan().getCommission(amount);
        totalAmount += senderCommission;

        double discount = baseAccount.getDiscountForTransactionCount(commerciant.getType());
        if (discount != 0) {
            totalAmount = totalAmount - amount * discount;
            baseAccount.invalidateCashback();
        }

        double amountInRON = Input.getInstance().getExchanges().convertCurrency(amount,
                baseAccount.getCurrency(), "RON");

        if (commerciant.getCashback() == CashbackPlans.SPENDING_THRESHOLD) {
            discount = baseAccount.getSpendingDiscount(commerciant, amountInRON);
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

        if (amountInRON > 300) {
            boolean result = user.increaseNumberOfOver300Payments();
            if (result) {
                baseAccount.getTransactionsHistory().add(new PlanUpgradeTransaction(
                        "Upgrade plan", getTimestamp(), "gold", account
                ));
            }

        }

        printLog("SendMoney:business", getTimestamp(), totalAmount, baseAccount.getBalance(),
                baseAccount.getIban());
    }
}
