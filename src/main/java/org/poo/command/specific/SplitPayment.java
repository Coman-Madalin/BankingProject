package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.specific.SplitTransaction;
import org.poo.user.Account;

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

        final SplitTransaction transaction = new SplitTransaction(
                String.format("Split payment of %.2f %s", amount, currency), getTimestamp(),
                amountPerPerson,
                currency,
                accounts);

        //TODO: if it is needed in the future, the accounts can be cached
        // TODO: DO THAT ^, BUT PUT THEM IN A SET

        for (final String accountNumber : accounts) {
            final Account account = input.getUsers().getAccountByIBAN(accountNumber);
            final double amountInAccountCurrency = input.getExchanges().convertCurrency(amountPerPerson,
                    currency, account.getCurrency());

            if (account.hasEnoughBalance(amountInAccountCurrency)) {
                account.decreaseBalance(amountInAccountCurrency);
            } else {
                transaction.setError(String.format("Account %s has insufficient funds for a split" +
                        " payment.", accountNumber));
            }
        }

        for (final String accountNumber : accounts) {
            final Account account = input.getUsers().getAccountByIBAN(accountNumber);
            account.getTransactionsHistory().add(transaction);
        }
    }
}
