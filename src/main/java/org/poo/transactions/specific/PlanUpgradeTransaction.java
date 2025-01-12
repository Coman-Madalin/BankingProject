package org.poo.transactions.specific;

import org.poo.transactions.BaseTransaction;

/**
 * The type Plan upgrade transaction.
 */
public class PlanUpgradeTransaction extends BaseTransaction {
    private final String newPlanType;
    private final String accountIBAN;

    /**
     * Instantiates a new Plan upgrade transaction.
     *
     * @param description the description
     * @param timestamp   the timestamp
     * @param newPlanType the new plan type
     * @param accountIBAN the account iban
     */
    public PlanUpgradeTransaction(String description, int timestamp, String newPlanType, String accountIBAN) {
        super(description, timestamp);
        this.newPlanType = newPlanType;
        this.accountIBAN = accountIBAN;
    }
}
