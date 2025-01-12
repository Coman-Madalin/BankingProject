package org.poo.transactions.specific;

import org.poo.transactions.BaseTransaction;

/**
 * The type Withdrawal transaction.
 */
public class WithdrawalTransaction extends BaseTransaction {
    /**
     * The Amount.
     */
    double amount;

    /**
     * Instantiates a new Withdrawal transaction.
     *
     * @param description the description
     * @param timestamp   the timestamp
     * @param amount      the amount
     */
    public WithdrawalTransaction(String description, int timestamp, double amount) {
        super(description, timestamp);
        this.amount = amount;
    }

    /**
     * Instantiates a new Withdrawal transaction.
     *
     * @param timestamp the timestamp
     * @param amount    the amount
     */
    public WithdrawalTransaction(int timestamp, double amount) {
        super("Cash withdrawal of " + amount, timestamp);
        this.amount = amount;
    }
}
