package org.poo.command.specific;

import org.poo.account.Account;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.user.User;

/**
 * Add an account command.
 */
public final class AddAccount extends BaseCommand {
    private final String email;
    private final String currency;
    private final String accountType;
    private final double interestRate;

    /**
     * Instantiates a new Add account.
     *
     * @param command     the command
     * @param timestamp   the timestamp
     * @param email       the email
     * @param currency    the currency
     * @param accountType the account type
     */
    public AddAccount(final String command, final int timestamp, final String email,
                      final String currency, final String accountType, double interestRate) {
        super(command, timestamp);
        this.email = email;
        this.currency = currency;
        this.accountType = accountType;
        this.interestRate = interestRate;
    }

    @Override
    public void execute() {
        final Input input = Input.getInstance();
        final User user = input.getUsers().getUserByEmail(email);
        if (user == null) {
            // TODO: user not found
            return;
        }
        Account account;
        if (accountType.equalsIgnoreCase("savings")) {
            account = new Account(currency, user, interestRate);
        } else {
            account = new Account(currency, accountType, user);
        }

        user.getAccounts().add(account);
        account.getTransactionsHistory().add(new BaseTransaction("New account created",
                getTimestamp()));

    }
}
