package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.specific.CardActionTransaction;
import org.poo.user.Account;
import org.poo.user.Card;
import org.poo.user.User;

public class DeleteCard extends BaseCommand {
    private String email;
    private String cardNumber;

    public DeleteCard(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
        final User user = input.getUsers().getUserByEmail(email);
        final Account account = input.getUsers().getAccountByEmailAndCardNumber(email, cardNumber);

        final Card card = user.deleteCardByCardNumber(cardNumber);
        if (card != null) {
            user.getTransactionsHistory().add(new CardActionTransaction(
                    "The card has been destroyed",
                    getTimestamp(),
                    account.getIBAN(),
                    card.getCardNumber(),
                    user.getEmail()
            ));
        }
    }
}
