package org.poo.command.specific;

import org.poo.account.BaseAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.specific.CardActionTransaction;
import org.poo.user.Card;

import static org.poo.input.Input.printLog;

/**
 * The type Delete card.
 */
public final class DeleteCard extends BaseCommand {
    private String email;
    private String cardNumber;

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
            printLog("DeleteCard:CardNotFound", getTimestamp(), -1, 0, cardNumber);
            return;
        }

        final BaseAccount account = card.getAccount();

        if (account.getType().equals("business")) {
            return;
        }

        if (account.getBalance() != 0) {
            printLog("DeleteCard:FoundFunds", getTimestamp(), -1, account.getBalance(),
                    cardNumber);

            // The Account has funds, so we don't delete the card, for some reason
            return;
        }

        if (!account.isValidEmail(email)) {
            System.out.println("Invalid email!");
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
