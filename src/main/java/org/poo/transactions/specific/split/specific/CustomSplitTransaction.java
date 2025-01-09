package org.poo.transactions.specific.split.specific;

import org.poo.transactions.specific.split.BaseSplitTransaction;

import java.util.List;

/**
 * The type Split transaction.
 */
public class CustomSplitTransaction extends BaseSplitTransaction {
    private List<Double> amountForUsers;

    public CustomSplitTransaction(String description, int timestamp, String currency,
                                  List<String> involvedAccounts, String splitPaymentType,
                                  List<Double> amountForUsers) {
        super(description, timestamp, currency, involvedAccounts, splitPaymentType);
        this.amountForUsers = amountForUsers;
    }

    public void addAmountForUsers(List<Double> amountForUsers) {
        this.amountForUsers = amountForUsers;
    }
}
