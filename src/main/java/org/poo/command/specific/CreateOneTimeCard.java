package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.specific.CardActionTransaction;
import org.poo.user.Account;
import org.poo.user.Card;
import org.poo.user.User;

public class CreateOneTimeCard extends BaseCommand {
    private String account;
    private String email;

    public CreateOneTimeCard(final String command, final int timestamp) {
        super(command, timestamp);
    }

    // TODO: now there is no way to know if it is one time or not
    @Override
    public void execute(final Input input) {
        final Card card = new Card();
        final User user = input.getUsers().getUserByEmail(email);
        final Account userAccount = input.getUsers().getAccountByEmailAndIBAN(email, account);

        if (userAccount != null) {
            userAccount.getCards().add(card);
            user.getTransactionsHistory().add(new CardActionTransaction(
                    "New card created",
                    getTimestamp(),
                    userAccount.getIBAN(),
                    card.getCardNumber(),
                    user.getEmail()
            ));
        }
    }
}
