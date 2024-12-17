package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.specific.SplitTransaction;
import org.poo.user.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Split payment.
 */
public final class SplitPayment extends BaseCommand {
    private double amount;
    private String currency;
    private List<String> accounts;

    /**
     * Instantiates a new Split payment.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public SplitPayment(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        Input input = Input.getInstance();
        final double amountPerPerson = amount / accounts.size();

        final SplitTransaction transaction = new SplitTransaction(
                String.format("Split payment of %.2f %s", amount, currency), getTimestamp(),
                amountPerPerson,
                currency,
                accounts);

        boolean everyoneCanPay = true;

        final List<Account> accountSet = new ArrayList<>();
        for (final String accountNumber : accounts) {
            final Account account = input.getUsers().getAccountByIBAN(accountNumber);
            accountSet.add(account);
            final double amountInAccountCurrency = input.getExchanges()
                    .convertCurrency(amountPerPerson, currency, account.getCurrency());

            if (!account.hasEnoughBalance(amountInAccountCurrency)) {
                transaction.setError(String.format("Account %s has insufficient funds for a split"
                        + " payment.", accountNumber));
                everyoneCanPay = false;
            }
        }

        for (final Account account : accountSet) {
            account.getTransactionsHistory().add(transaction);

            if (everyoneCanPay) {
                final double amountInAccountCurrency = input.getExchanges()
                        .convertCurrency(amountPerPerson, currency, account.getCurrency());
                account.decreaseBalance(amountInAccountCurrency);
            }
        }
    }
}
