package org.poo.command.specific;

import org.poo.account.Account;
import org.poo.command.BaseCommand;
import org.poo.input.Input;

/**
 * The type Add funds.
 */
public final class AddFunds extends BaseCommand {
    private String account;
    private int amount;

    /**
     * Instantiates a new Add funds.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public AddFunds(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        final Input input = Input.getInstance();
        final Account userAccount = input.getUsers().getAccountByIBAN(account);

        if (userAccount != null) {
            userAccount.increaseBalance(amount);
        }
    }
}
