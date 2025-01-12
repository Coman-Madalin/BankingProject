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
    public InterestTransaction(final String description, final int timestamp, final double amount,
                               final String currency) {
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
    public InterestTransaction(final int timestamp, final double amount, final String currency) {
        super("Interest rate income", timestamp);
        this.amount = amount;
        this.currency = currency;
    }
}
