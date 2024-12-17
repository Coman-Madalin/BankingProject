package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.user.Account;

/**
 * The type Add interest.
 */
public final class AddInterest extends BaseCommand {
    private String account;

    /**
     * Instantiates a new Add interest.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public AddInterest(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        final Input input = Input.getInstance();
        final Account userAccount = input.getUsers().getAccountByIBAN(account);

        if (!userAccount.getType().equals("savings")) {
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("timestamp", getTimestamp());
            outputJson.addProperty("description", "This is not a savings account");
            setOutput(outputJson.toString());
            return;
        }

        userAccount.increaseBalance(userAccount.getBalance() * userAccount.getInterestRate());
    }
}
