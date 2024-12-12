package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.transactions.specific.PaymentTransaction;
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

    private void setOutputAsError() {
        final JsonObject outputJson = new JsonObject();
        outputJson.addProperty("description", "Card not found");
        outputJson.addProperty("timestamp", this.getTimestamp());
        this.setOutput(outputJson.toString());
    }

    @Override
    public void execute(final Input input) {
        final Account account = input.getUsers().getAccountByEmailAndCardNumber(email, cardNumber);

        if (account == null) {
            setOutputAsError();
            return;
        }

        final Card card = account.getCardByCardNumber(cardNumber);
        if (card == null) {
            setOutputAsError();
            return;
        }

        final double sameCurrencyAmount = input.getExchanges().convertCurrency(amount,
                currency, account.getCurrency());

        final User user = input.getUsers().getUserByEmail(email);

        if (!account.hasEnoughBalance(sameCurrencyAmount)) {
            user.getTransactionsHistory().add(new BaseTransaction(
                    "Insufficient funds",
                    getTimestamp()
            ));
            return;
        }

        user.getTransactionsHistory().add(new PaymentTransaction(
                "Card payment",
                getTimestamp(),
                sameCurrencyAmount,
                commerciant
        ));
        account.decreaseBalance(sameCurrencyAmount);
    }
}
