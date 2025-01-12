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
        final Card card = Input.getInstance().getUsers().getCardByEmailAndCardNumber(email, cardNumber);

        if (card == null) {
            printLog("DeleteCard:CardNotFound", getTimestamp(), -1, 0, cardNumber);
            //TODO: Card not found
            return;
        }

        final BaseAccount account = card.getAccount();

//        if (account.getBalance() != 0) {
//            printLog("DeleteCard:FoundFunds", getTimestamp(), -1, account.getBalance(), cardNumber);
//
//            //TODO: account has funds, so we don't delete card, for some reason
//            return;
//        }

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
