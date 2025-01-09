package org.poo.transactions.specific;

import org.poo.transactions.BaseTransaction;

public class PlanUpgradeTransaction extends BaseTransaction {
    private final String newPlanType;
    private final String accountIBAN;

    public PlanUpgradeTransaction(String description, int timestamp, String newPlanType, String accountIBAN) {
        super(description, timestamp);
        this.newPlanType = newPlanType;
        this.accountIBAN = accountIBAN;
    }
}
