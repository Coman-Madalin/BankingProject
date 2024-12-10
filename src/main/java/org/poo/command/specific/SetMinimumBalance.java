package org.poo.command.specific;

import org.poo.Input;
import org.poo.command.BaseCommand;
import org.poo.user.Account;
import org.poo.user.User;

public class SetMinimumBalance extends BaseCommand {
    private String account;
    private int amount;

    public SetMinimumBalance(String command, int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(Input input) {
        for (User user : input.getUsers()) {
            for (Account userAccount : user.getAccounts()) {
                if (userAccount.getIBAN().equals(this.account)){
                    userAccount.setMinBalance(amount);
                    return;
                }
            }
        }
    }
}
