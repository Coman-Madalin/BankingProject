package org.poo.command.specific;

import org.poo.account.BaseAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;

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
        final Input input = Input.getInstance();
        final BaseAccount userAccount = input.getUsers().getAccountByIBAN(account);

        if (userAccount == null) {
            return;
        }

        userAccount.setMinBalance(amount);
    }
}
