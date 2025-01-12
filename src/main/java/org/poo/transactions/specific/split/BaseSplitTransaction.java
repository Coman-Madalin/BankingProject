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
    public BaseSplitTransaction(String description, int timestamp, String currency, List<String> involvedAccounts, String splitPaymentType) {
        super(description, timestamp);
        this.currency = currency;
        this.involvedAccounts = involvedAccounts;
        this.splitPaymentType = splitPaymentType;
    }


    /**
     * Add error.
     *
     * @param error the error
     */
    public final void addError(String error) {
        this.error = error;
    }
}
