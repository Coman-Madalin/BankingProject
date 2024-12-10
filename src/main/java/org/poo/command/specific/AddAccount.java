package org.poo.command.specific;

import org.poo.Input;
import org.poo.command.BaseCommand;
import org.poo.user.Account;
import org.poo.user.User;

import static org.poo.utils.Utils.generateIBAN;

public class AddAccount extends BaseCommand {
    String email;
    String currency;
    String accountType;

    public AddAccount(String command, int timestamp, String email, String currency,
                      String accountType) {
        super(command, timestamp);
        this.email = email;
        this.currency = currency;
        this.accountType = accountType;
    }

    @Override
    public void execute(Input input) {
        Account account = new Account();
        account.setIBAN(generateIBAN());
        account.setCurrency(this.currency);
        account.setType(this.accountType);

        for (User inputUser : input.getUsers()) {
            if ( inputUser.getEmail().equals(this.email)){
                inputUser.getAccounts().add(account);
                return;
            }
        }
    }
}
