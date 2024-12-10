package org.poo.command.specific;

import org.poo.Input;
import org.poo.command.BaseCommand;
import org.poo.user.Account;
import org.poo.user.User;

public class AddFunds extends BaseCommand {
    String account;
    int amount;

    public AddFunds(String command, int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(Input input) {
        for (User user : input.getUsers()) {
            for (Account userAccount : user.getAccounts()) {
                if (userAccount.getIBAN().equals(this.account)){
                    userAccount.increaseBalance(this.amount);
                    return;
                }
            }
        }
    }
}
