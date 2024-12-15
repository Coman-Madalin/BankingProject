package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
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

        if (senderUser == null || receiverUser == null) {
            return;
        }

        final Account senderAccount = input.getUsers().getAccountByEmailAndIBAN(email, account);
        final Account receiverAccount = input.getUsers().getAccountByIBAN(receiver);


        if (senderAccount == null || receiverAccount == null) {
            return;
        }

        if (isAlias(account) && !isAlias(receiver)) {
            return;
        }

        if (isAlias(account) || isAlias(receiver)) {
            if (!senderUser.equals(receiverUser)) {
                return;
            }
        }

        if (!senderAccount.hasEnoughBalance(amount)) {
            senderUser.getTransactionsHistory().add(new BaseTransaction(
                    getTimestamp()
            ));
            return;
        }

        senderAccount.decreaseBalance(amount);

        final double receiverCurrencyAmount = input.getExchanges().convertCurrency(amount,
                senderAccount.getCurrency(), receiverAccount.getCurrency());

        receiverAccount.increaseBalance(receiverCurrencyAmount);

        senderUser.getTransactionsHistory().add(new TransferTransaction(
                description,
                getTimestamp(),
                account,
                receiver,
                String.format("%.1f %s", amount, senderAccount.getCurrency()),
//                amount + " " + senderAccount.getCurrency(),
                "sent"
        ));

    }
}
