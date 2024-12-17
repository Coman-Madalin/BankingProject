package org.poo.transactions.specific;

import lombok.Getter;
import lombok.Setter;
import org.poo.transactions.BaseTransaction;

import java.util.List;

/**
 * The type Split transaction.
 */
public class SplitTransaction extends BaseTransaction {
    private final double amount;
    private final String currency;
    private final List<String> involvedAccounts;
    @Setter
    @Getter
    private String error = null;

    /**
     * Instantiates a new Split transaction.
     *
     * @param description      the description
     * @param timestamp        the timestamp
     * @param amount           the amount
     * @param currency         the currency
     * @param involvedAccounts the involved accounts
     */
    public SplitTransaction(final String description, final int timestamp, final double amount,
                            final String currency, final List<String> involvedAccounts) {
        super(description, timestamp);
        this.amount = amount;
        this.currency = currency;
        this.involvedAccounts = involvedAccounts;
    }

}
