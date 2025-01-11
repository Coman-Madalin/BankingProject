package org.poo.transactions.specific;

import org.poo.transactions.BaseTransaction;

public class WithdrawSavingsTransaction extends BaseTransaction {
    private final double amount;
    private final String classicAccountIBAN;
    private final String savingsAccountIBAN;

    public WithdrawSavingsTransaction(String description, int timestamp, double amount,
                                      String classicAccountIBAN, String savingsAccountIBAN) {
        super(description, timestamp);
        this.amount = amount;
        this.classicAccountIBAN = classicAccountIBAN;
        this.savingsAccountIBAN = savingsAccountIBAN;
    }
}
