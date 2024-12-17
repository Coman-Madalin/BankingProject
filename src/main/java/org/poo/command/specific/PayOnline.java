package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.transactions.specific.PaymentTransaction;
import org.poo.user.Account;
import org.poo.user.Card;

/**
 * The type Pay online.
 */
public final class PayOnline extends BaseCommand {
    private String cardNumber;
    private double amount;
    private String currency;
    private String description;
    private String commerciant;
    private String email;

    /**
     * Instantiates a new Pay online.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
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
    public void execute() {
        final Input input = Input.getInstance();
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

        if (card.getStatus().equals("frozen")) {
            account.getTransactionsHistory().add(new BaseTransaction("The card is frozen",
                    getTimestamp()));
            return;
        }

        final double sameCurrencyAmount = input.getExchanges().convertCurrency(amount,
                currency, account.getCurrency());

        if (!account.hasEnoughBalance(sameCurrencyAmount)) {
            account.getTransactionsHistory().add(new BaseTransaction(
                    getTimestamp()
            ));
            return;
        }

        account.getTransactionsHistory().add(new PaymentTransaction(
                "Card payment",
                getTimestamp(),
                sameCurrencyAmount,
                commerciant
        ));

        account.decreaseBalance(sameCurrencyAmount);

        if (card.isOneTimeCard()) {
            new DeleteCard("deleteCard", getTimestamp(), email, cardNumber).execute();
            new CreateOneTimeCard("createOneTimeCard", getTimestamp(), account.getIban(), email)
                    .execute();
        }

    }
}
