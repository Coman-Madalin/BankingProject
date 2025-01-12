package org.poo.user;

import lombok.Getter;

/**
 * The type Data.
 */
@Getter
public class Data {
    private int nrTransactions = 0;
    private double totalSpend = 0;
    private String type = null;

    /**
     * Instantiates a new Data.
     */
    public Data() {
    }

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
     * Instantiates a new Data.
     *
     * @param type           the type
     * @param nrTransactions the nr transactions
     * @param totalSpend     the total spend
     */
    public Data(final String type, final int nrTransactions, final double totalSpend) {
        this.nrTransactions = nrTransactions;
        this.totalSpend = totalSpend;
        this.type = type;
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

    /**
     * Increase transactions.
     */
    public void increaseTransactions() {
        this.nrTransactions++;
    }

    /**
     * Increase amount.
     *
     * @param amount the amount
     */
    public void increaseAmount(final double amount) {
        this.totalSpend += amount;
    }
}
