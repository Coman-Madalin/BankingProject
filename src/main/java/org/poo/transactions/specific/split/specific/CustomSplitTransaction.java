package org.poo.transactions.specific.split.specific;

import org.poo.transactions.specific.split.BaseSplitTransaction;

import java.util.List;

/**
 * The type Custom split transaction.
 */
public class CustomSplitTransaction extends BaseSplitTransaction {
    private List<Double> amountForUsers;

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
    public CustomSplitTransaction(String description, int timestamp, String currency,
                                  List<String> involvedAccounts, String splitPaymentType,
                                  List<Double> amountForUsers) {
        super(description, timestamp, currency, involvedAccounts, splitPaymentType);
        this.amountForUsers = amountForUsers;
    }

    /**
     * Add amount for users.
     *
     * @param amountForUsers the amount for users
     */
    public void addAmountForUsers(List<Double> amountForUsers) {
        this.amountForUsers = amountForUsers;
    }
}
