package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.specific.CardActionTransaction;
import org.poo.user.Account;
import org.poo.user.Card;
import org.poo.user.User;

/**
 * The type Create one time card.
 */
public final class CreateOneTimeCard extends BaseCommand {
    private String account;
    private String email;

    /**
     * Instantiates a new Create one time card.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public CreateOneTimeCard(final String command, final int timestamp) {
        super(command, timestamp);
    }

    /**
     * Instantiates a new Create one time card.
     *
     * @param command   the command
     * @param timestamp the timestamp
     * @param account   the account
     * @param email     the email
     */
    public CreateOneTimeCard(final String command, final int timestamp, final String account,
                             final String email) {
        super(command, timestamp);
        this.account = account;
        this.email = email;
    }

    @Override
    public void execute() {
        final Input input = Input.getInstance();
        final Card card = new Card(true);
        final User user = input.getUsers().getUserByEmail(email);
        final Account userAccount = input.getUsers().getAccountByEmailAndIBAN(email, account);

        if (userAccount != null) {
            userAccount.getCards().add(card);
            userAccount.getTransactionsHistory().add(new CardActionTransaction(
                    "New card created",
                    getTimestamp(),
                    userAccount.getIban(),
                    card.getCardNumber(),
                    user.getEmail()
            ));
        }
    }
}
