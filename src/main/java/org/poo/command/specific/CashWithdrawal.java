package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.user.Account;
import org.poo.user.Card;
import org.poo.user.User;

public class CashWithdrawal extends BaseCommand {
    private String email;
    private String cardNumber;
    private double amount;
    private String location;

    /**
     * Instantiates a new Base command.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public CashWithdrawal(String command, int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        if(getTimestamp() == 10){
            System.out.println("dada");
        }

        User user = Input.getInstance().getUsers().getUserByEmail(email);

        if (user == null) {
            return;
        }

        Account account = user.getAccountByCardNumber(cardNumber);

        if (account == null) {
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("description", "Card not found");
            outputJson.addProperty("timestamp", this.getTimestamp());
            this.setOutput(outputJson.toString());
            return;
        }

        Card card = account.getCardByCardNumber(cardNumber);
        if (card == null) {
            return;
        }

        double accountCurrencyAmount = Input.getInstance().getExchanges()
                .convertCurrency(amount, "RON", account.getCurrency());
        double commission = user.getServicePlan().getCommission(accountCurrencyAmount);

        if (!account.hasEnoughBalance(accountCurrencyAmount)) {
            account.getTransactionsHistory().add(new BaseTransaction(
                    "Insufficient funds", getTimestamp()
            ));
            return;
        }

        account.decreaseBalance(accountCurrencyAmount);
    }
}
