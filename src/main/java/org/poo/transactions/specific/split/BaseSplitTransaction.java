package org.poo.transactions.specific.split;

import lombok.Getter;
import lombok.Setter;
import org.poo.transactions.BaseTransaction;

import java.util.List;

/**
 * The type Base split transaction.
 */
public class BaseSplitTransaction extends BaseTransaction {
    private final String currency;
    private final List<String> involvedAccounts;
    private final String splitPaymentType;
    @Setter
    @Getter
    private String error = null;

    /**
     * Instantiates a new Base split transaction.
     *
     * @param description      the description
     * @param timestamp        the timestamp
     * @param currency         the currency
     * @param involvedAccounts the involved accounts
     * @param splitPaymentType the split payment type
     */
    public BaseSplitTransaction(final String description, final int timestamp,
                                final String currency, final List<String> involvedAccounts,
                                final String splitPaymentType) {
        super(description, timestamp);
        this.currency = currency;
        this.involvedAccounts = involvedAccounts;
        this.splitPaymentType = splitPaymentType;
    }

    /**
     * Add errorMessage.
     *
     * @param errorMessage the errorMessage
     */
    public final void addError(final String errorMessage) {
        this.error = errorMessage;
    }
}
