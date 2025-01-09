package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.account.BaseAccount;
import org.poo.account.specific.SavingsAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;

/**
 * The type Change interest rate.
 */
public final class ChangeInterestRate extends BaseCommand {
    private String account;
    private double interestRate;

    /**
     * Instantiates a new Change interest rate.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public ChangeInterestRate(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        final Input input = Input.getInstance();
        final BaseAccount baseAccount = input.getUsers().getAccountByIBAN(account);

        if (!baseAccount.getType().equals("savings")) {
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("timestamp", getTimestamp());
            outputJson.addProperty("description", "This is not a savings account");
            setOutput(outputJson.toString());
            return;
        }

        SavingsAccount savingsAccount = (SavingsAccount) baseAccount;

        savingsAccount.setInterestRate(interestRate);
        savingsAccount.getTransactionsHistory().add(new BaseTransaction(
                String.format("Interest rate of the account changed to %.2f", interestRate),
                getTimestamp()
        ));
    }
}
