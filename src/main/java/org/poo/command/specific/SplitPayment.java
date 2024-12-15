package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.specific.SplitTransaction;
import org.poo.user.Account;
import org.poo.user.User;

import java.util.List;

public class SplitPayment extends BaseCommand {
    private double amount;
    private String currency;
    private List<String> accounts;

    public SplitPayment(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
        final double amountPerPerson = amount / accounts.size();

        for (final String accountNumber : accounts) {
            final User user = input.getUsers().getUserByIBAN(accountNumber);
            final Account account = input.getUsers().getAccountByIBAN(accountNumber);
            final double amountInAccountCurrency = input.getExchanges().convertCurrency(amountPerPerson,
                    currency, account.getCurrency());

            if (account.hasEnoughBalance(amountInAccountCurrency)) {
                account.decreaseBalance(amountInAccountCurrency);
                user.getTransactionsHistory().add(new SplitTransaction(
                        String.format("Split payment of %.2f %s", amount, currency), getTimestamp(),
                        amountPerPerson,
                        currency,
                        accounts

                ));
            } else {
                // TODO: this account doesnt have enough money
            }
        }
    }
}
