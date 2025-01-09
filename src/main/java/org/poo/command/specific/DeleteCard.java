package org.poo.command.specific;

import org.poo.account.Account;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.specific.CardActionTransaction;
import org.poo.user.Card;
import org.poo.user.User;

/**
 * The type Delete card.
 */
public final class DeleteCard extends BaseCommand {
    private String email;
    private String cardNumber;

    /**
     * Instantiates a new Delete card.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public DeleteCard(final String command, final int timestamp) {
        super(command, timestamp);
    }

    /**
     * Instantiates a new Delete card.
     *
     * @param command    the command
     * @param timestamp  the timestamp
     * @param email      the email
     * @param cardNumber the card number
     */
    public DeleteCard(final String command, final int timestamp, final String email,
                      final String cardNumber) {
        super(command, timestamp);
        this.email = email;
        this.cardNumber = cardNumber;
    }

    @Override
    public void execute() {
        Input input = Input.getInstance();
        final User user = input.getUsers().getUserByEmail(email);
        final Account account = input.getUsers().getAccountByEmailAndCardNumber(email, cardNumber);

        final Card card = user.deleteCardByCardNumber(cardNumber);
        if (card != null) {
            account.getTransactionsHistory().add(new CardActionTransaction(
                    "The card has been destroyed",
                    getTimestamp(),
                    account.getIban(),
                    card.getCardNumber(),
                    user.getEmail()
            ));
        }
    }
}
