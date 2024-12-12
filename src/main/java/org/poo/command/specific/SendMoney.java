package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.specific.TransferTransaction;
import org.poo.user.Account;
import org.poo.user.User;

import static org.poo.input.Input.isAlias;

public class SendMoney extends BaseCommand {
    private String account;
    private String receiver;
    private double amount;
    private String email;
    private String description;

    public SendMoney(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
        // TODO: Maybe check first time for user using email and then on it check for account
        final User senderUser = input.getUsers().getUserByEmail(email);
        final User receiverUser = input.getUsers().getUserByIBAN(receiver);
        final Account senderAccount = input.getUsers().getAccountByEmailAndIBAN(email, account);
        final Account receiverAccount = input.getUsers().getAccountByIBAN(receiver);

        if (isAlias(account) && !isAlias(receiver)) {
            return;
        }

        if (isAlias(account) || isAlias(receiver)) {
            if (!senderUser.equals(receiverUser)) {
                return;
            }
        }

        if (!senderAccount.hasEnoughBalance(amount)) {
            // TODO: Make an failed transaction for this account
            return;
        }

        senderAccount.decreaseBalance(amount);

        final double receiverCurrencyAmount = input.getExchanges().convertCurrency(amount,
                senderAccount.getCurrency(), receiverAccount.getCurrency());

        receiverAccount.increaseBalance(receiverCurrencyAmount);

        senderUser.getTransactionsHistory().add(new TransferTransaction(
                getTimestamp(),
                description,
                account,
                receiver,
                amount + " " + senderAccount.getCurrency(),
                "sent"
        ));

    }
}
