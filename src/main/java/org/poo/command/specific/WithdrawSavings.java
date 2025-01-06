package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.user.Account;
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
        User user = Input.getInstance().getUsers().getUserByIBAN(account);
        Account accountUser = Input.getInstance().getUsers().getAccountByIBAN(account);

        if (user.getAge() < MINIMUM_AGE) {
            accountUser.getTransactionsHistory().add(new BaseTransaction(
                    "You don't have the minimum age required.", getTimestamp()
            ));
        }

    }
}
