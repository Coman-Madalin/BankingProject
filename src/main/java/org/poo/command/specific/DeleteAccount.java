package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.Input;
import org.poo.command.BaseCommand;
import org.poo.user.Account;
import org.poo.user.User;

public class DeleteAccount extends BaseCommand {
    private String email;
    private String account;

    public DeleteAccount(String command, int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(Input input) {
        for (User inputUser : input.getUsers()) {
            if (!inputUser.getEmail().equals(this.email)) {
                continue;
            }
            for (Account inputUserAccount : inputUser.getAccounts()) {
                if (inputUserAccount.getIBAN().equals(this.account)){
                    inputUser.getAccounts().remove(inputUserAccount);

                    JsonObject outputJson = new JsonObject();
                    outputJson.addProperty("success", "Account deleted");
                    outputJson.addProperty("timestamp", this.getTimestamp());
                    this.setOutput(outputJson.toString());

                    return;
                }
            }
        }
    }
}
