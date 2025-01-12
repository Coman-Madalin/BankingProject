package org.poo.transactions.specific;

import org.poo.transactions.BaseTransaction;

/**
 * The type Interest transaction.
 */
public class InterestTransaction extends BaseTransaction {
    private final double amount;
    private final String currency;

    /**
     * Instantiates a new Interest transaction.
     *
     * @param description the description
     * @param timestamp   the timestamp
     * @param amount      the amount
     * @param currency    the currency
     */
    public InterestTransaction(String description, int timestamp, double amount, String currency) {
        super(description, timestamp);
        this.amount = amount;
        this.currency = currency;
    }

    /**
     * Instantiates a new Interest transaction.
     *
     * @param timestamp the timestamp
     * @param amount    the amount
     * @param currency  the currency
     */
    public InterestTransaction(int timestamp, double amount, String currency) {
        super("Interest rate income", timestamp);
        this.amount = amount;
        this.currency = currency;
    }
}
