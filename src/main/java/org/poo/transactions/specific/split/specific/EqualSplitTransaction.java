package org.poo.transactions.specific.split.specific;

import org.poo.transactions.specific.split.BaseSplitTransaction;

import java.util.List;

public class EqualSplitTransaction extends BaseSplitTransaction {
    private double amount;

    public EqualSplitTransaction(String description, int timestamp, String currency,
                                 List<String> involvedAccounts, String splitPaymentType,
                                 double amount) {
        super(description, timestamp, currency, involvedAccounts, splitPaymentType);
        this.amount = amount;
    }

    public void addAmount(double amount) {
        this.amount = amount;
    }
}
