package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.user.Account;
import org.poo.user.Card;

/**
 * The type Check card status.
 */
public final class CheckCardStatus extends BaseCommand {
    private String cardNumber;

    /**
     * Instantiates a new Check card status.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public CheckCardStatus(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
        final Card card = input.getUsers().getCardByCardNumber(cardNumber);
        final Account account = input.getUsers().getAccountByCardNumber(cardNumber);
        if (card == null) {
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("timestamp", getTimestamp());
            outputJson.addProperty("description", "Card not found");
            this.setOutput(outputJson.toString());
            return;
        }
        if (account.getBalance() <= account.getMinBalance()) {
            card.setStatus("frozen");
            account.getTransactionsHistory().add(new BaseTransaction("You have reached "
                    + "the minimum amount of funds, the card will be frozen", getTimestamp()));
        }
    }
}
