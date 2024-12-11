package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.user.User;

public class DeleteAccount extends BaseCommand {
    private String email;
    private String account;

    public DeleteAccount(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
        final User user = input.getUsers().getUserByEmail(email);

        if (user.deleteAccountByIBAN(account)) {
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("success", "Account deleted");
            outputJson.addProperty("timestamp", this.getTimestamp());
            this.setOutput(outputJson.toString());
        }
        // TODO: complete else branch if an account with the specified IBAN doesn't exist for user
    }
}
