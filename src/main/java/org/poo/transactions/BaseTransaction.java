package org.poo.transactions;

import lombok.Getter;

/**
 * The type Base transaction.
 */
@Getter
public class BaseTransaction implements Comparable<BaseTransaction> {
    private final String description;
    private final int timestamp;

    /**
     * Instantiates a new Base transaction.
     *
     * @param description the description
     * @param timestamp   the timestamp
     */
    public BaseTransaction(final String description, final int timestamp) {
        this.description = description;
        this.timestamp = timestamp;
    }

    /**
     * Instantiates a new Base transaction.
     *
     * @param timestamp the timestamp
     */
    public BaseTransaction(final int timestamp) {
        this.description = "Insufficient funds";
        this.timestamp = timestamp;
    }

    @Override
    public final int compareTo(final BaseTransaction o) {
        return this.timestamp - o.getTimestamp();
    }
}
