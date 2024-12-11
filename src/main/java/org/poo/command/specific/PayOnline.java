package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.input.Input;
import org.poo.command.BaseCommand;
import org.poo.user.Account;
import org.poo.user.Card;
import org.poo.user.User;

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
        for (final User user : input.getUsers()) {
            if (!user.getEmail().equals(this.email)) {
                continue;
            }

            for (final Account userAccount : user.getAccounts()) {
                for (final Card card : userAccount.getCards()) {
                    if (card.getCardNumber().equals(this.cardNumber)) {
                        final double sameCurrencyAmount = amount;
                        if (!currency.equals(userAccount.getCurrency())) {
//                            sameCurrencyAmount = input.exchangeCurrency(amount, currency,
//                                    userAccount.getCurrency());
                        }

                        userAccount.decreaseBalance(sameCurrencyAmount);
                        return;
                    }
                }
            }
        }
        final JsonObject outputJson = new JsonObject();
        outputJson.addProperty("description", "Card not found");
        outputJson.addProperty("timestamp", this.getTimestamp());
        this.setOutput(outputJson.toString());
    }
}
