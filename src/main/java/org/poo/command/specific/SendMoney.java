package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.user.Account;

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
        final Account senderAccount = input.getUsers().getAccountByEmailAndIBAN(email, account);
        final Account receiverAccount = input.getUsers().getAccountByIBAN(receiver);

        if (!senderAccount.hasEnoughBalance(amount)) {
            return;
        }
        senderAccount.decreaseBalance(amount);

        final double receiverCurrencyAmount = input.getExchanges().convertCurrency(amount,
                senderAccount.getCurrency(), receiverAccount.getCurrency());

        receiverAccount.increaseBalance(receiverCurrencyAmount);
    }
}
