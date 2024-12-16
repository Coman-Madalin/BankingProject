package org.poo.transactions.specific;

import lombok.Getter;
import lombok.Setter;
import org.poo.transactions.BaseTransaction;

import java.util.List;

public class SplitTransaction extends BaseTransaction {
    private final double amount;
    private final String currency;
    private final List<String> involvedAccounts;
    @Setter
    @Getter
    private String error = null;

    public SplitTransaction(final String description, final int timestamp, final double amount, final String currency,
                            final List<String> involvedAccounts) {
        super(description, timestamp);
        this.amount = amount;
        this.currency = currency;
        this.involvedAccounts = involvedAccounts;
    }

}
