package org.poo.transactions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseTransaction implements Comparable<BaseTransaction> {
    private String description;
    private int timestamp;

    public BaseTransaction(final String description, final int timestamp) {
        this.description = description;
        this.timestamp = timestamp;
    }

    public BaseTransaction(final int timestamp) {
        this.description = "Insufficient funds";
        this.timestamp = timestamp;
    }

    // This is more or less a safe check, it shouldn't be necessary in a single-threaded environment
    @Override
    public int compareTo(final BaseTransaction o) {
        return this.timestamp - o.getTimestamp();
    }
}
