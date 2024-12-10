package org.poo.command.specific;

import org.poo.Input;
import org.poo.command.BaseCommand;
import org.poo.user.Account;
import org.poo.user.User;

import static org.poo.utils.Utils.generateIBAN;

public class AddAccount extends BaseCommand {
    private String email;
    private String currency;
    private String accountType;

    public AddAccount(final String command, final int timestamp, final String email, final String currency,
                      final String accountType) {
        super(command, timestamp);
        this.email = email;
        this.currency = currency;
        this.accountType = accountType;
    }

    @Override
    public void execute(final Input input) {
        final Account account = new Account();
        account.setIBAN(generateIBAN());
        account.setCurrency(this.currency);
        account.setType(this.accountType);

        for (final User inputUser : input.getUsers()) {
            if ( inputUser.getEmail().equals(this.email)){
                inputUser.getAccounts().add(account);
                return;
            }
        }
    }
}
