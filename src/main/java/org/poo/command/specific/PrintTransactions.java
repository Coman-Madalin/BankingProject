package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.json.JsonUtils;
import org.poo.user.User;

public class PrintTransactions extends BaseCommand {
    private String email;

    public PrintTransactions(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
        final User user = input.getUsers().getUserByEmail(email);
        setOutput(JsonUtils.getGson().toJson(user.getTransactionsHistory()));
    }
}
