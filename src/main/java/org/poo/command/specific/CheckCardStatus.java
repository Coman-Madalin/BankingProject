package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.user.Account;
import org.poo.user.Card;
import org.poo.user.User;

public class CheckCardStatus extends BaseCommand {
    private String cardNumber;

    public CheckCardStatus(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
        final Card card = input.getUsers().getCardByCardNumber(cardNumber);
        final Account account = input.getUsers().getAccountByCardNumber(cardNumber);
        final User user = input.getUsers().getUserByCardNumber(cardNumber);
        if (card == null) {
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("timestamp", getTimestamp());
            outputJson.addProperty("description", "Card not found");
            this.setOutput(outputJson.toString());
            return;
        }
        if (account.getBalance() <= account.getMinBalance()) {
            card.setStatus("frozen");
            user.getTransactionsHistory().add(new BaseTransaction("You have reached the minimum " +
                    "amount of funds, the card will be frozen", getTimestamp()));
//            final JsonObject outputJson = new JsonObject();
//            outputJson.addProperty("timestamp", getTimestamp());
//            outputJson.addProperty("description", "You have reached the minimum amount of funds, the card will be frozen");
//            this.setOutput(outputJson.toString());
            return;
        }

        if (account.getBalance() <= account.getMinBalance() + 30) {
            card.setStatus("warning");
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("timestamp", getTimestamp());
            outputJson.addProperty("description", "You are close to limit the card will be " +
                    "warning");
            this.setOutput(outputJson.toString());
        }
    }
}
