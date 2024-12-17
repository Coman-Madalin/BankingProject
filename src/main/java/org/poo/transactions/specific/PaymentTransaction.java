package org.poo.transactions.specific;

import lombok.Getter;
import org.poo.transactions.BaseTransaction;

/**
 * The type Payment transaction.
 */
@Getter
public class PaymentTransaction extends BaseTransaction {
    private final double amount;
    private final String commerciant;

    /**
     * Instantiates a new Payment transaction.
     *
     * @param description the description
     * @param timestamp   the timestamp
     * @param amount      the amount
     * @param commerciant the commerciant
     */
    public PaymentTransaction(final String description, final int timestamp, final double amount,
                              final String commerciant) {
        super(description, timestamp);
        this.amount = amount;
        this.commerciant = commerciant;
    }
}
