package org.poo.transactions.specific.split.specific;

import org.poo.transactions.specific.split.BaseSplitTransaction;

import java.util.List;

/**
 * The type Custom split transaction.
 */
public class CustomSplitTransaction extends BaseSplitTransaction {
    private final List<Double> amountForUsers;

    /**
     * Instantiates a new Custom split transaction.
     *
     * @param description      the description
     * @param timestamp        the timestamp
     * @param currency         the currency
     * @param involvedAccounts the involved accounts
     * @param splitPaymentType the split payment type
     * @param amountForUsers   the amount for users
     */
    public CustomSplitTransaction(final String description, final int timestamp,
                                  final String currency, final List<String> involvedAccounts,
                                  final String splitPaymentType,
                                  final List<Double> amountForUsers) {
        super(description, timestamp, currency, involvedAccounts, splitPaymentType);
        this.amountForUsers = amountForUsers;
    }
}
