package org.poo.transactions.specific;

import lombok.Getter;
import org.poo.transactions.BaseTransaction;

@Getter
public class PaymentTransaction extends BaseTransaction {
    private final double amount;
    private final String commerciant;

    public PaymentTransaction(final String description, final int timestamp, final double amount,
                              final String commerciant) {
        super(description, timestamp);
        this.amount = amount;
        this.commerciant = commerciant;
    }
}
