package org.poo.command.specific;

import org.poo.account.BaseAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.transactions.specific.WithdrawSavingsTransaction;
import org.poo.user.User;

import static org.poo.input.Input.printLog;

public class WithdrawSavings extends BaseCommand {
    private final static short MINIMUM_AGE = 21;

    private String account;
    private double amount;
    private String currency;

    /**
     * Instantiates a new Base command.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public WithdrawSavings(String command, int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        BaseAccount senderAccount = Input.getInstance().getUsers().getAccountByIBAN(account);
        User user = senderAccount.getUser();

        if (user.getAge() < MINIMUM_AGE) {
            senderAccount.getTransactionsHistory().add(new BaseTransaction(
                    "You don't have the minimum age required.", getTimestamp()
            ));
        }

        BaseAccount receiverAccount = user.getClassicAccountInCurrency(currency);

        if (receiverAccount == null) {
            senderAccount.getTransactionsHistory().add(new BaseTransaction(
                    "You do not have a classic account.",
                    getTimestamp()
            ));
            return;
        }

        double amountInRON = Input.getInstance().getExchanges()
                .convertCurrency(amount, currency, "RON");
        double commissionInRON = senderAccount.getUser().getServicePlan()
                .getCommission(amountInRON);
        double commissionInSenderCurrency = Input.getInstance().getExchanges()
                .convertCurrency(commissionInRON, "RON", senderAccount.getCurrency());
        double amountInSenderCurrency = Input.getInstance().getExchanges()
                .convertCurrency(amount, currency, senderAccount.getCurrency());
        double totalAmount = amountInSenderCurrency + commissionInSenderCurrency;

        if (!senderAccount.hasEnoughBalance(totalAmount)) {
            printLog("WithdrawSavings:NotEnoughBalance", getTimestamp(), totalAmount,
                    senderAccount.getBalance(), senderAccount.getIban());
            return;
        }

        senderAccount.decreaseBalance(totalAmount);
        receiverAccount.increaseBalance(amount);

        WithdrawSavingsTransaction transaction = new WithdrawSavingsTransaction(
                "Savings withdrawal",
                getTimestamp(),
                amount,
                receiverAccount.getIban(),
                senderAccount.getIban()
        );

        senderAccount.getTransactionsHistory().add(transaction);
        receiverAccount.getTransactionsHistory().add(transaction);
    }
}
