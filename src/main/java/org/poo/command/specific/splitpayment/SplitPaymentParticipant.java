package org.poo.command.specific.splitpayment;

import lombok.Getter;
import lombok.Setter;
import org.poo.transactions.specific.SplitTransaction;
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
        return account.hasEnoughBalance(amount);
    }

    public void proceedPayment() {
        account.decreaseBalance(amount);
        createTransactionLog();

        removePayment();
    }

    private void removePayment() {
        account.getUser().removeSplitPayment(this);
    }

    private void createTransactionLog() {
        account.getTransactionsHistory().add(new SplitTransaction(
                "Split payment of " + mediator.getPaymentCommand().getAmount() + " " + currency,
                mediator.getPaymentCommand().getTimestamp(),
                mediator.getPaymentCommand().getAmountForUsers(),
                currency,
                mediator.getPaymentCommand().getAccounts(),
                mediator.getPaymentCommand().getSplitPaymentType()
        ));
    }
}
