package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.account.BaseAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.transactions.specific.WithdrawalTransaction;
import org.poo.user.Card;
import org.poo.user.User;

import static org.poo.input.Input.printLog;

/**
 * The type Cash withdrawal.
 */
public class CashWithdrawal extends BaseCommand {
    private String email;
    private String cardNumber;
    private double amount;
    private String location;

    /**
     * Instantiates a new Cash withdrawal.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public CashWithdrawal(String command, int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        User user = Input.getInstance().getUsers().getUserByEmail(email);

        if (user == null) {
            JsonObject outputJson = new JsonObject();
            outputJson.addProperty("description", "User not found");
            outputJson.addProperty("timestamp", getTimestamp());
            setOutput(outputJson.toString());
            return;
        }

        Card card = user.getCardByCardNumber(cardNumber);

        if (card == null) {
            printLog("CashWithdrawal:CardNotFound", getTimestamp(), 0, 0, cardNumber);

            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("description", "Card not found");
            outputJson.addProperty("timestamp", this.getTimestamp());
            this.setOutput(outputJson.toString());
            return;
        }

        BaseAccount account = card.getAccount();

        double amountAccountCurrency = Input.getInstance().getExchanges()
                .convertCurrency(amount, "RON", account.getCurrency());
        double commission = user.getServicePlan().getCommission(amount);
        double commissionAccountCurrency = Input.getInstance().getExchanges()
                .convertCurrency(commission, "RON", account.getCurrency());
        double totalAmount = amountAccountCurrency + commissionAccountCurrency;

        if (!account.hasEnoughBalance(totalAmount)) {
            account.getTransactionsHistory().add(new BaseTransaction(
                    "Insufficient funds", getTimestamp()
            ));
            return;
        }

        account.decreaseBalance(totalAmount);
        account.getTransactionsHistory().add(new WithdrawalTransaction(getTimestamp(), amount));
        printLog("CashWithdrawal", getTimestamp(), totalAmount, account.getBalance(), account.getIban());
    }
}
