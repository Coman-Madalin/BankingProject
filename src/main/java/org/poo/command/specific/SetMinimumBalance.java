package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.user.Account;

public class SetMinimumBalance extends BaseCommand {
    private String account;
    private int amount;

    public SetMinimumBalance(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
        final Account userAccount = input.getUsers().getAccountByIBAN(account);

        if (userAccount == null) {
            return;
        }

        userAccount.setMinBalance(amount);
    }
}
