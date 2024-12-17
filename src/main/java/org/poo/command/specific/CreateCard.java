package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.specific.CardActionTransaction;
import org.poo.user.Account;
import org.poo.user.Card;
import org.poo.user.User;

public class CreateCard extends BaseCommand {
    private String account;
    private String email;

    public CreateCard(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
        final User user = input.getUsers().getUserByEmail(email);
        final Account userAccount = input.getUsers().getAccountByEmailAndIBAN(email, account);

        if (userAccount != null) {
            final Card card = new Card();

            userAccount.getCards().add(card);
            userAccount.getTransactionsHistory().add(new CardActionTransaction(
                    "New card created",
                    getTimestamp(),
                    userAccount.getIBAN(),
                    card.getCardNumber(),
                    user.getEmail()
            ));
        }
    }
}
