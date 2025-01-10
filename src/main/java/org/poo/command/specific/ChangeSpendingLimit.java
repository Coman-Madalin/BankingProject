package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.account.specific.BusinessAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;

public class ChangeSpendingLimit extends BaseCommand {
    private String email;
    private String account;
    private double amount;

    /**
     * Instantiates a new Base command.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public ChangeSpendingLimit(String command, int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        BusinessAccount businessAccount = (BusinessAccount) Input.getInstance().getUsers()
                .getAccountByIBAN(account);

        if (businessAccount == null) {
            //TODO: business account not found
            return;
        }

        if (!businessAccount.getUser().getEmail().equals(email)) {
            JsonObject outputJson = new JsonObject();
            outputJson.addProperty("description",
                    "You must be owner in order to change spending limit.");
            outputJson.addProperty("timestamp", getTimestamp());
            setOutput(outputJson.toString());
            return;
        }

        businessAccount.setSpendingLimit(amount);
    }
}
