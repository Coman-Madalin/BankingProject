package org.poo.command.specific;

import org.poo.account.BaseAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.specific.CardActionTransaction;
import org.poo.user.Card;
import org.poo.user.User;

/**
 * The type Create one time card.
 */
public final class CreateOneTimeCard extends BaseCommand {
    private final String account;
    private final String email;

    /**
     * Instantiates a new Create one time card.
     *
     * @param timestamp the timestamp
     * @param account   the account
     * @param email     the email
     */
    public CreateOneTimeCard(final int timestamp, final String account, final String email) {
        super("createOneTimeCard", timestamp);
        this.account = account;
        this.email = email;
    }

    @Override
    public void execute() {
        final Input input = Input.getInstance();
        final BaseAccount userAccount = input.getUsers().getAccountByEmailAndIBAN(email, account);

        if (userAccount == null) {
            return;
        }

        final User user = userAccount.getUser();
        final Card card = new Card(true, userAccount);

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
