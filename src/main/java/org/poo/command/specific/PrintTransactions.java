package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.json.JsonUtils;
import org.poo.user.User;

/**
 * The type Print transactions.
 */
public final class PrintTransactions extends BaseCommand {
    private String email;

    /**
     * Instantiates a new Print transactions.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public PrintTransactions(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        Input input = Input.getInstance();
        final User user = input.getUsers().getUserByEmail(email);

        setOutput(JsonUtils.getGSON().toJson(user.getTransactionsHistory()));
    }
}
