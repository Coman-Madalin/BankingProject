package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.user.Account;

/**
 * The type Set minimum balance.
 */
public final class SetMinimumBalance extends BaseCommand {
    private String account;
    private int amount;

    /**
     * Instantiates a new Set minimum balance.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public SetMinimumBalance(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        Input input = Input.getInstance();
        final Account userAccount = input.getUsers().getAccountByIBAN(account);

        if (userAccount == null) {
            return;
        }

        userAccount.setMinBalance(amount);
    }
}
