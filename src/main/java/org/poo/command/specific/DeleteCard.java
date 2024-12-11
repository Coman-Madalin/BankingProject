package org.poo.command.specific;

import org.poo.input.Input;
import org.poo.command.BaseCommand;
import org.poo.user.Account;
import org.poo.user.Card;
import org.poo.user.User;

public class DeleteCard extends BaseCommand {
    private String email;
    private String cardNumber;

    public DeleteCard(final String command, final int timestamp) {
        super(command, timestamp);
    }

    // TODO: make methods to search for a specific User, Account and Card

    @Override
    public void execute(final Input input) {
        for (final User inputUser : input.getUsers()) {
            if (!inputUser.getEmail().equals(this.email)) {
                continue;
            }
            for (final Account inputUserAccount : inputUser.getAccounts()) {
                for (final Card card : inputUserAccount.getCards()) {
                    if (card.getCardNumber().equals(this.cardNumber)) {
                        inputUserAccount.getCards().remove(card);

//                        JsonObject outputJson = new JsonObject();
//                        outputJson.addProperty("success", "Account deleted");
//                        outputJson.addProperty("timestamp", this.getTimestamp());
//                        this.setOutput(outputJson.toString());

                        return;
                    }
                }


            }
        }
    }
}
