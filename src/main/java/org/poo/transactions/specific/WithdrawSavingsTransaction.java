package org.poo.transactions.specific;

import org.poo.transactions.BaseTransaction;

/**
 * The type Withdraw savings transaction.
 */
public class WithdrawSavingsTransaction extends BaseTransaction {
    private final double amount;
    private final String classicAccountIBAN;
    private final String savingsAccountIBAN;

    /**
     * Instantiates a new Withdraw savings transaction.
     *
     * @param description        the description
     * @param timestamp          the timestamp
     * @param amount             the amount
     * @param classicAccountIBAN the classic account iban
     * @param savingsAccountIBAN the savings account iban
     */
    public WithdrawSavingsTransaction(final String description, final int timestamp,
                                      final double amount, final String classicAccountIBAN,
                                      final String savingsAccountIBAN) {
        super(description, timestamp);
        this.amount = amount;
        this.classicAccountIBAN = classicAccountIBAN;
        this.savingsAccountIBAN = savingsAccountIBAN;
    }
}
