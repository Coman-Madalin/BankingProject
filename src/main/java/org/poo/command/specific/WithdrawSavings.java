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
            return;
        }

        BaseAccount receiverAccount = user.getClassicAccountInCurrency(currency);

        if (receiverAccount == null) {
            senderAccount.getTransactionsHistory().add(new BaseTransaction(
                    "You do not have a classic account.",
                    getTimestamp()
            ));
            return;
        }
        double amountInSenderCurrency = Input.getInstance().getExchanges()
                .convertCurrency(amount, currency, senderAccount.getCurrency());

        if (!senderAccount.hasEnoughBalance(amountInSenderCurrency)) {
            printLog("WithdrawSavings:NotEnoughBalance", getTimestamp(), amountInSenderCurrency,
                    senderAccount.getBalance(), senderAccount.getIban());
            return;
        }

        senderAccount.decreaseBalance(amountInSenderCurrency);
        receiverAccount.increaseBalance(amount);

        WithdrawSavingsTransaction transaction = new WithdrawSavingsTransaction(
                "Savings withdrawal",
                getTimestamp(),
                amount,
                receiverAccount.getIban(),
                senderAccount.getIban()
        );

        printLog("WithdrawSavings, FROM:", getTimestamp(), amountInSenderCurrency,
                senderAccount.getBalance(), senderAccount.getIban());
        printLog("WithdrawSavings, TO:", getTimestamp(), amount,
                receiverAccount.getBalance(), receiverAccount.getIban());

        senderAccount.getTransactionsHistory().add(transaction);
        receiverAccount.getTransactionsHistory().add(transaction);
    }
}
