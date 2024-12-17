package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.user.Account;
import org.poo.user.User;

/**
 * The type Delete account.
 */
public final class DeleteAccount extends BaseCommand {
    private String email;
    private String account;

    /**
     * Instantiates a new Delete account.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
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
        } else {
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("error",
                    "Account couldn't be deleted - see org.poo.transactions for details");
            outputJson.addProperty("timestamp", this.getTimestamp());
            this.setOutput(outputJson.toString());

            final Account userAccount = input.getUsers().getAccountByIBAN(account);
            userAccount.getTransactionsHistory().add(new BaseTransaction(
                    "Account couldn't be deleted - there are funds remaining",
                    getTimestamp()
            ));
        }
    }
}
