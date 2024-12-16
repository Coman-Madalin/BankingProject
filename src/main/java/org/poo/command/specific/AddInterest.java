package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.user.Account;

public class AddInterest extends BaseCommand {
    private String account;

    public AddInterest(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
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
