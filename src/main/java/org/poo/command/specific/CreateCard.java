package org.poo.command.specific;

import org.poo.Input;
import org.poo.command.BaseCommand;
import org.poo.user.Account;
import org.poo.user.Card;
import org.poo.user.User;

import static org.poo.utils.Utils.generateCardNumber;

public class CreateCard extends BaseCommand {
    String account;
    String email;

    public CreateCard(String command, int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(Input input) {
        Card card = new Card();
        card.setCardNumber(generateCardNumber());
        card.setStatus("active");
        for (User user : input.getUsers()) {
            if (!user.getEmail().equals(this.email)) {
                continue;
            }

            for (Account userAccount : user.getAccounts()) {
                if (userAccount.getIBAN().equals(this.account)) {
                    userAccount.getCards().add(card);
                    return;
                }
            }
        }
    }
}
