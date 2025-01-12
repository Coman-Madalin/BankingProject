package org.poo.transactions.specific.split.specific;

import org.poo.transactions.specific.split.BaseSplitTransaction;

import java.util.List;

/**
 * The type Equal split transaction.
 */
public class EqualSplitTransaction extends BaseSplitTransaction {
    private final double amount;

    /**
     * Instantiates a new Equal split transaction.
     *
     * @param description      the description
     * @param timestamp        the timestamp
     * @param currency         the currency
     * @param involvedAccounts the involved accounts
     * @param splitPaymentType the split payment type
     * @param amount           the amount
     */
    public EqualSplitTransaction(String description, int timestamp, String currency,
                                 List<String> involvedAccounts, String splitPaymentType,
                                 double amount) {
        super(description, timestamp, currency, involvedAccounts, splitPaymentType);
        this.amount = amount;
    }
}
