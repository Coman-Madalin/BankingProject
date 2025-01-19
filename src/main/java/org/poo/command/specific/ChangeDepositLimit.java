package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.account.specific.BusinessAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;

/**
 * The type Change deposit limit.
 */
public final class ChangeDepositLimit extends BaseCommand {
    private String email;
    private String account;
    private double amount;

    /**
     * Instantiates a new Change deposit limit.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public ChangeDepositLimit(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        final BusinessAccount businessAccount = (BusinessAccount) Input.getInstance().getUsers()
                .getAccountByIBAN(account);

        if (!businessAccount.getUser().getEmail().equals(email)) {
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("description",
                    "You must be owner in order to change deposit limit.");
            outputJson.addProperty("timestamp", getTimestamp());
            setOutput(outputJson.toString());
            return;
        }

        businessAccount.setDepositLimit(amount);
    }
}
