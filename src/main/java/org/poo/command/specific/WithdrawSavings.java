package org.poo.command.specific;

import org.poo.account.Account;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.user.User;

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
        Account senderAccount = Input.getInstance().getUsers().getAccountByIBAN(account);
        User user = senderAccount.getUser();

        if (user.getAge() < MINIMUM_AGE) {
            senderAccount.getTransactionsHistory().add(new BaseTransaction(
                    "You don't have the minimum age required.", getTimestamp()
            ));
        }

        Account receiverAccount = user.getClassicAccountInCurrency(currency);
        if (receiverAccount == null) {
            senderAccount.getTransactionsHistory().add(new BaseTransaction(
                    "You do not have a classic account.",
                    getTimestamp()
            ));
        }

    }
}
