package org.poo.transactions.specific;

import org.poo.transactions.BaseTransaction;

public class WithdrawalTransaction extends BaseTransaction {
    double amount;

    public WithdrawalTransaction(String description, int timestamp, double amount) {
        super(description, timestamp);
        this.amount = amount;
    }

    public WithdrawalTransaction(int timestamp, double amount) {
        super("Cash withdrawal of " + amount, timestamp);
        this.amount = amount;
    }
}
