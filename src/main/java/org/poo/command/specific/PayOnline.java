package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.user.Account;
import org.poo.user.Card;

public class PayOnline extends BaseCommand {
    private String cardNumber;
    private double amount;
    private String currency;
    private String description;
    private String commerciant;
    private String email;

    public PayOnline(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
        final Account account = input.getUsers().getAccountByEmailAndCardNumber(email, cardNumber);

        if (account == null) {
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("description", "Card not found");
            outputJson.addProperty("timestamp", this.getTimestamp());
            this.setOutput(outputJson.toString());
            return;
        }

        final Card card = account.getCardByCardNumber(cardNumber);

        final double sameCurrencyAmount = input.getExchanges().convertCurrency(amount,
                currency, account.getCurrency());

        account.decreaseBalance(sameCurrencyAmount);
    }
}
