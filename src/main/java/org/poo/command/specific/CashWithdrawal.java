package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.account.BaseAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.transactions.specific.WithdrawalTransaction;
import org.poo.user.Card;
import org.poo.user.User;

/**
 * The type Cash withdrawal.
 */
public final class CashWithdrawal extends BaseCommand {
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
    public CashWithdrawal(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        final User user = Input.getInstance().getUsers().getUserByEmail(email);

        if (user == null) {
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("description", "User not found");
            outputJson.addProperty("timestamp", getTimestamp());
            setOutput(outputJson.toString());
            return;
        }

        final Card card = user.getCardByCardNumber(cardNumber);

        if (card == null) {
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("description", "Card not found");
            outputJson.addProperty("timestamp", this.getTimestamp());
            this.setOutput(outputJson.toString());
            return;
        }

        final BaseAccount account = card.getAccount();

        final double amountAccountCurrency = Input.getInstance().getExchanges()
                .convertCurrency(amount, "RON", account.getCurrency());
        final double commission = user.getServicePlan().getCommission(amount);
        final double commissionAccountCurrency = Input.getInstance().getExchanges()
                .convertCurrency(commission, "RON", account.getCurrency());
        final double totalAmount = amountAccountCurrency + commissionAccountCurrency;

        if (!account.hasEnoughBalance(totalAmount)) {
            account.getTransactionsHistory().add(new BaseTransaction(
                    "Insufficient funds", getTimestamp()
            ));
            return;
        }

        account.decreaseBalance(totalAmount);
        account.getTransactionsHistory().add(new WithdrawalTransaction(getTimestamp(), amount));
    }
}
