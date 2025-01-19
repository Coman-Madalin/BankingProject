package org.poo.command.specific;

import org.poo.account.BaseAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.specific.CardActionTransaction;
import org.poo.user.Card;

/**
 * The type Delete card.
 */
public final class DeleteCard extends BaseCommand {
    private final String email;
    private final String cardNumber;

    /**
     * Instantiates a new Delete card.
     *
     * @param timestamp  the timestamp
     * @param email      the email
     * @param cardNumber the card number
     */
    public DeleteCard(final int timestamp, final String email, final String cardNumber) {
        super("deleteCard", timestamp);
        this.email = email;
        this.cardNumber = cardNumber;
    }

    @Override
    public void execute() {
        final Card card = Input.getInstance().getUsers().getCardByCardNumber(cardNumber);

        if (card == null) {
            return;
        }

        final BaseAccount account = card.getAccount();

        if (account.getType().equals("business")) {
            return;
        }

        if (account.getBalance() != 0) {
            return;
        }

        if (!account.isValidEmail(email)) {
            return;
        }

        account.getCards().remove(card);

        account.getTransactionsHistory().add(new CardActionTransaction(
                "The card has been destroyed",
                getTimestamp(),
                account.getIban(),
                card.getCardNumber(),
                account.getUser().getEmail()
        ));
    }

    /**
     * Force execute.
     */
    public void forceExecute() {
        final Card card = Input.getInstance().getUsers().getCardByCardNumber(cardNumber);
        final BaseAccount account = card.getAccount();

        account.getCards().remove(card);

        account.getTransactionsHistory().add(new CardActionTransaction(
                "The card has been destroyed",
                getTimestamp(),
                account.getIban(),
                card.getCardNumber(),
                account.getUser().getEmail()
        ));
    }
}
