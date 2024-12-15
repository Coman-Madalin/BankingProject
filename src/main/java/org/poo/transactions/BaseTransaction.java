package org.poo.transactions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseTransaction {
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

}
