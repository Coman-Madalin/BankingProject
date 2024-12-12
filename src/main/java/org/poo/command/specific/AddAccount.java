package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.user.Account;
import org.poo.user.User;

public class AddAccount extends BaseCommand {
    private final String email;
    private final String currency;
    private final String accountType;

    public AddAccount(final String command, final int timestamp, final String email, final String currency,
                      final String accountType) {
        super(command, timestamp);
        this.email = email;
        this.currency = currency;
        this.accountType = accountType;
    }

    @Override
    public void execute(final Input input) {
        final Account account = new Account(currency, accountType);
        final User user = input.getUsers().getUserByEmail(email);

        if (user != null) {
            user.getAccounts().add(account);
            user.getTransactionsHistory().add(new BaseTransaction("New account created",
                    getTimestamp()));
        }
    }
}
