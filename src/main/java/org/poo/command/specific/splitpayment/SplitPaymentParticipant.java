package org.poo.command.specific.splitpayment;

import lombok.Getter;
import lombok.Setter;
import org.poo.account.BaseAccount;
import org.poo.input.Input;
import org.poo.transactions.specific.split.BaseSplitTransaction;
import org.poo.transactions.specific.split.specific.CustomSplitTransaction;
import org.poo.transactions.specific.split.specific.EqualSplitTransaction;

/**
 * The type Split payment participant.
 */
public class SplitPaymentParticipant {
    @Getter
    private final BaseAccount account;
    private final double amount;
    private final String currency;
    @Getter
    @Setter
    private SplitPaymentInstance mediator;

    /**
     * Instantiates a new Split payment participant.
     *
     * @param account  the account
     * @param amount   the amount
     * @param currency the currency
     */
    public SplitPaymentParticipant(BaseAccount account, double amount, String currency) {
        this.account = account;
        this.amount = amount;
        this.currency = currency;
    }

    /**
     * Reject payment.
     */
    public void rejectPayment() {
        mediator.notify(this, false);
    }

    /**
     * Accept payment.
     */
    public void acceptPayment() {
        mediator.notify(this, true);
    }

    /**
     * Check for funds boolean.
     *
     * @return the boolean
     */
    public boolean checkForFunds() {
        double amountInParticipantCurrency = Input.getInstance().getExchanges()
                .convertCurrency(amount, currency, account.getCurrency());
        return account.hasEnoughBalance(amountInParticipantCurrency);
    }

    /**
     * Proceed payment.
     */
    public void proceedPayment() {
        double amountInParticipantCurrency = Input.getInstance().getExchanges()
                .convertCurrency(amount, currency, account.getCurrency());
        account.decreaseBalance(amountInParticipantCurrency);
        BaseSplitTransaction splitTransaction = createTransactionLog();
        account.getTransactionsHistory().add(splitTransaction);

        removePayment();
    }

    /**
     * Invalidate payment.
     *
     * @param errorMessage the error message
     */
    public void invalidatePayment(String errorMessage) {
        BaseSplitTransaction splitTransaction = createTransactionLog();
        splitTransaction.addError(errorMessage);
        account.getTransactionsHistory().add(splitTransaction);

        removePayment();
    }

    private void removePayment() {
        account.getUser().removeSplitPayment(this);
    }

    private BaseSplitTransaction createTransactionLog() {
        BaseSplitTransaction transaction = null;

        switch (mediator.getPaymentCommand().getSplitPaymentType()) {
            case "custom" -> transaction = new CustomSplitTransaction(
                    String.format("Split payment of %.2f %s",
                            mediator.getPaymentCommand().getAmount(), currency),
                    mediator.getPaymentCommand().getTimestamp(),
                    currency,
                    mediator.getPaymentCommand().getAccounts(),
                    mediator.getPaymentCommand().getSplitPaymentType(),
                    mediator.getPaymentCommand().getAmountForUsers()
            );

            case "equal" -> transaction = new EqualSplitTransaction(
                    String.format("Split payment of %.2f %s",
                            mediator.getPaymentCommand().getAmount(), currency),
                    mediator.getPaymentCommand().getTimestamp(),
                    currency,
                    mediator.getPaymentCommand().getAccounts(),
                    mediator.getPaymentCommand().getSplitPaymentType(),
                    mediator.getPaymentCommand().getAmountForUsers().get(0)
            );

            default -> System.out.println("ERROR: UNSUPPORTED SPLIT PAYMENT TYPE!");

        }

        return transaction;
    }
}
