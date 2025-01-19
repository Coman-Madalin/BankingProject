package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.account.BaseAccount;
import org.poo.account.specific.BusinessAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;

/**
 * The type Change spending limit.
 */
public final class ChangeSpendingLimit extends BaseCommand {
    private String email;
    private String account;
    private double amount;

    /**
     * Instantiates a new Change spending limit.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public ChangeSpendingLimit(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        final BaseAccount baseAccount = Input.getInstance().getUsers().getAccountByIBAN(account);
        if (baseAccount == null) {
            return;
        }

        if (!baseAccount.getType().equalsIgnoreCase("business")) {
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("description", "This is not a business account");
            outputJson.addProperty("timestamp", getTimestamp());
            setOutput(outputJson.toString());

            return;
        }

        final BusinessAccount businessAccount = (BusinessAccount) baseAccount;

        if (!businessAccount.getUser().getEmail().equals(email)) {
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("description",
                    "You must be owner in order to change spending limit.");
            outputJson.addProperty("timestamp", getTimestamp());
            setOutput(outputJson.toString());
            return;
        }

        businessAccount.setSpendingLimit(amount);
    }
}
