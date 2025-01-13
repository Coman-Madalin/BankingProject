package org.poo.user;

import lombok.Getter;

/**
 * The type Data.
 */
@Getter
public class Data {
    private int nrTransactions = 0;
    private double totalSpend = 0;

    /**
     * Instantiates a new Data.
     *
     * @param nrTransactions the nr transactions
     * @param totalSpend     the total spend
     */
    public Data(final int nrTransactions, final double totalSpend) {
        this.nrTransactions = nrTransactions;
        this.totalSpend = totalSpend;
    }

    /**
     * Add transaction.
     *
     * @param amount the amount
     */
    public void addTransaction(final double amount) {
        this.nrTransactions++;
        this.totalSpend += amount;
    }
}
