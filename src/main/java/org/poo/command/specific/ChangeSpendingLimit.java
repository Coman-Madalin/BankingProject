package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.account.BaseAccount;
import org.poo.account.specific.BusinessAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;

import static org.poo.input.Input.printLog;

/**
 * The type Change spending limit.
 */
public class ChangeSpendingLimit extends BaseCommand {
    private String email;
    private String account;
    private double amount;

    /**
     * Instantiates a new Change spending limit.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public ChangeSpendingLimit(String command, int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        BaseAccount baseAccount = Input.getInstance().getUsers().getAccountByIBAN(account);
        if (baseAccount == null) {
            printLog("ChangeSpendingLimit:AccountNotFound", getTimestamp(), -1, -1, account);
            return;
        }

        if (!baseAccount.getType().equalsIgnoreCase("business")) {
            printLog("ChangeSpendingLimit:NotBusinessAccount", getTimestamp(), -1, -1, account);
//            baseAccount.getTransactionsHistory().add(new BaseTransaction(
//                    "This is not a business account",
//                    getTimestamp()
//            ));
            JsonObject outputJson = new JsonObject();
            outputJson.addProperty("description", "This is not a business account");
            outputJson.addProperty("timestamp", getTimestamp());
            setOutput(outputJson.toString());

            return;
        }

        BusinessAccount businessAccount = (BusinessAccount) baseAccount;

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
