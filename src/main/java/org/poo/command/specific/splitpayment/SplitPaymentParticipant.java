package org.poo.command.specific.splitpayment;

import lombok.Getter;
import lombok.Setter;
import org.poo.input.Input;
import org.poo.transactions.specific.split.BaseSplitTransaction;
import org.poo.transactions.specific.split.specific.CustomSplitTransaction;
import org.poo.transactions.specific.split.specific.EqualSplitTransaction;
import org.poo.user.Account;

public class SplitPaymentParticipant {
    @Getter
    private Account account;
    private double amount;
    private String currency;
    @Getter
    @Setter
    private SplitPaymentInstance mediator;

    public SplitPaymentParticipant(Account account, double amount, String currency) {
        this.account = account;
        this.amount = amount;
        this.currency = currency;
    }

    public void rejectPayment() {
        mediator.notify(this, false);
    }

    public void acceptPayment() {
        mediator.notify(this, true);
    }

    public boolean checkForFunds() {
        double amountInParticipantCurrency = Input.getInstance().getExchanges()
                .convertCurrency(amount, currency, account.getCurrency());
        return account.hasEnoughBalance(amountInParticipantCurrency);
    }

    public void proceedPayment() {
        double amountInParticipantCurrency = Input.getInstance().getExchanges()
                .convertCurrency(amount, currency, account.getCurrency());
        account.decreaseBalance(amountInParticipantCurrency);
        BaseSplitTransaction splitTransaction = createTransactionLog();
        account.getTransactionsHistory().add(splitTransaction);

        removePayment();
    }

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
                    "Split payment of " + mediator.getPaymentCommand().getAmount() + " " + currency,
                    mediator.getPaymentCommand().getTimestamp(),
                    currency,
                    mediator.getPaymentCommand().getAccounts(),
                    mediator.getPaymentCommand().getSplitPaymentType(),
                    mediator.getPaymentCommand().getAmountForUsers()
            );

            case "equal" -> transaction = new EqualSplitTransaction(
                    "Split payment of " + mediator.getPaymentCommand().getAmount() + " " + currency,
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
