package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.account.BaseAccount;
import org.poo.account.specific.SavingsAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.specific.InterestTransaction;

import static org.poo.input.Input.printLog;

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
        BaseAccount baseAccount = input.getUsers().getAccountByIBAN(account);

        if (!baseAccount.getType().equals("savings")) {
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("timestamp", getTimestamp());
            outputJson.addProperty("description", "This is not a savings account");
            setOutput(outputJson.toString());
            return;
        }

        SavingsAccount savingsAccount = (SavingsAccount) baseAccount;

        double amountToAdd = savingsAccount.getBalance() * savingsAccount.getInterestRate();
        savingsAccount.increaseBalance(amountToAdd);
        savingsAccount.getTransactionsHistory().add(new InterestTransaction(
                getTimestamp(),
                amountToAdd,
                savingsAccount.getCurrency()
        ));

        printLog("AddInterest", getTimestamp(), amountToAdd, savingsAccount.getBalance(),
                savingsAccount.getIban());
    }
}
