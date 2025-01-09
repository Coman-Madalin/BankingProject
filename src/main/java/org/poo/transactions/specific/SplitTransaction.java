package org.poo.transactions.specific;

import lombok.Getter;
import lombok.Setter;
import org.poo.transactions.BaseTransaction;

import java.util.List;

/**
 * The type Split transaction.
 */
public class SplitTransaction extends BaseTransaction {
    private final List<Double> amountForUsers;
    private final String currency;
    private final List<String> involvedAccounts;
    private final String splitPaymentType;
    @Setter
    @Getter
    private String error = null;


    public SplitTransaction(String description, int timestamp, List<Double> amountForUsers,
                            String currency, List<String> involvedAccounts,
                            String splitPaymentType) {
        super(description, timestamp);
        this.amountForUsers = amountForUsers;
        this.currency = currency;
        this.involvedAccounts = involvedAccounts;
        this.splitPaymentType = splitPaymentType;
    }
}
