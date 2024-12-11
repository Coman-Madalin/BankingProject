package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.user.Account;
import org.poo.user.Card;

public class CreateCard extends BaseCommand {
    private String account;
    private String email;

    public CreateCard(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
        final Card card = new Card();
        final Account userAccount = input.getUsers().getAccountByEmailAndIBAN(email, account);

        if (userAccount != null) {
            userAccount.getCards().add(card);
        }
    }
}
