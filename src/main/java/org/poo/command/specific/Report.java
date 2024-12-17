package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.json.JsonUtils;
import org.poo.transactions.BaseTransaction;
import org.poo.user.Account;

import java.util.*;

public class Report extends BaseCommand {
    private String account;
    private int startTimestamp;
    private int endTimestamp;

    public Report(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
        final Account userAccount = input.getUsers().getAccountByIBAN(account);

        final JsonObject outputObject = new JsonObject();

        if (userAccount == null) {
            outputObject.addProperty("timestamp", getTimestamp());
            outputObject.addProperty("description", "Account not found");
            setOutput(outputObject.toString());
            return;
        }

        outputObject.addProperty("IBAN", account);
        outputObject.addProperty("balance", userAccount.getBalance());
        outputObject.addProperty("currency", userAccount.getCurrency());
        final List<BaseTransaction> transactions = new ArrayList<>();
        for (final BaseTransaction baseTransaction : userAccount.getTransactionsHistory()) {
            if (baseTransaction.getTimestamp() >= startTimestamp &&
                    baseTransaction.getTimestamp() <= endTimestamp) {
                transactions.add(baseTransaction);
            }
        }

        outputObject.add("transactions", JsonUtils.getGson().toJsonTree(transactions));

        setOutput(outputObject.toString());
    }
}
