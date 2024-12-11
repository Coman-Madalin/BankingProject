package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;
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

        user.deleteCardByCardNumber(cardNumber);
    }
}
