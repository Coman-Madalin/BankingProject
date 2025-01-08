package org.poo.transactions.specific;

import org.poo.transactions.BaseTransaction;

public class InterestTransaction extends BaseTransaction {
    private double amount;
    private String currency;

    public InterestTransaction(String description, int timestamp, double amount, String currency) {
        super(description, timestamp);
        this.amount = amount;
        this.currency = currency;
    }

    public InterestTransaction(int timestamp, double amount, String currency) {
        super("Interest rate income", timestamp);
        this.amount = amount;
        this.currency = currency;
    }
}
