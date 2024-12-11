package org.poo.command.specific;

import org.poo.input.Input;
import org.poo.command.BaseCommand;
import org.poo.user.Account;
import org.poo.user.User;

public class AddFunds extends BaseCommand {
    private String account;
    private int amount;

    public AddFunds(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
        for (final User user : input.getUsers()) {
            for (final Account userAccount : user.getAccounts()) {
                if (userAccount.getIBAN().equals(this.account)){
                    userAccount.increaseBalance(this.amount);
                    return;
                }
            }
        }
    }
}
