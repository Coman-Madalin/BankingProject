package org.poo.command.specific;

import org.poo.account.BaseAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.transactions.specific.WithdrawSavingsTransaction;
import org.poo.user.User;

/**
 * The type Withdraw savings.
 */
public final class WithdrawSavings extends BaseCommand {
    private static final short MINIMUM_AGE = 21;

    private String account;
    private double amount;
    private String currency;

    /**
     * Instantiates a new Withdraw savings.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public WithdrawSavings(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        final BaseAccount senderAccount = Input.getInstance().getUsers().getAccountByIBAN(account);
        final User user = senderAccount.getUser();

        if (user.getAge() < MINIMUM_AGE) {
            senderAccount.getTransactionsHistory().add(new BaseTransaction(
                    "You don't have the minimum age required.", getTimestamp()
            ));
            return;
        }

        final BaseAccount receiverAccount = user.getClassicAccountInCurrency(currency);

        if (receiverAccount == null) {
            senderAccount.getTransactionsHistory().add(new BaseTransaction(
                    "You do not have a classic account.",
                    getTimestamp()
            ));
            return;
        }
        final double amountInSenderCurrency = Input.getInstance().getExchanges()
                .convertCurrency(amount, currency, senderAccount.getCurrency());

        if (!senderAccount.hasEnoughBalance(amountInSenderCurrency)) {
            return;
        }

        senderAccount.decreaseBalance(amountInSenderCurrency);
        receiverAccount.increaseBalance(amount);

        final WithdrawSavingsTransaction transaction = new WithdrawSavingsTransaction(
                "Savings withdrawal",
                getTimestamp(),
                amount,
                receiverAccount.getIban(),
                senderAccount.getIban()
        );

        senderAccount.getTransactionsHistory().add(transaction);
        receiverAccount.getTransactionsHistory().add(transaction);
    }
}
