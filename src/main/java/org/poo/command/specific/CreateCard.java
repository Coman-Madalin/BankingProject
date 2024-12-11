package org.poo.command.specific;

import org.poo.input.Input;
import org.poo.command.BaseCommand;
import org.poo.user.Account;
import org.poo.user.Card;
import org.poo.user.User;

import static org.poo.utils.Utils.generateCardNumber;

public class CreateCard extends BaseCommand {
    private String account;
    private String email;

    public CreateCard(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
        final Card card = new Card();
        card.setCardNumber(generateCardNumber());
        card.setStatus("active");
        for (final User user : input.getUsers()) {
            if (!user.getEmail().equals(this.email)) {
                continue;
            }

            for (final Account userAccount : user.getAccounts()) {
                if (userAccount.getIBAN().equals(this.account)) {
                    userAccount.getCards().add(card);
                    return;
                }
            }
        }
    }
}
