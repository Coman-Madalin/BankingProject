package org.poo.transactions.specific.split;

import lombok.Getter;
import lombok.Setter;
import org.poo.transactions.BaseTransaction;

import java.util.List;

public class BaseSplitTransaction extends BaseTransaction {
    private final String currency;
    private final List<String> involvedAccounts;
    private final String splitPaymentType;
    @Setter
    @Getter
    private String error = null;

    public BaseSplitTransaction(String description, int timestamp, String currency, List<String> involvedAccounts, String splitPaymentType) {
        super(description, timestamp);
        this.currency = currency;
        this.involvedAccounts = involvedAccounts;
        this.splitPaymentType = splitPaymentType;
    }


    public final void addError(String error) {
        this.error = error;
    }
}
