package org.poo.commerciant;

/**
 * The enum Cashback plans.
 */
public enum CashbackPlans {
    /**
     * Nr of transactions cashback plan.
     */
    NR_OF_TRANSACTIONS,
    /**
     * Spending threshold cashback plan.
     */
    SPENDING_THRESHOLD,
    /**
     * No cashback plan.
     * This is a placeholder for cases where no cashback plan is applicable.
     */
    NONE;

    /**
     * Parse cashback plan.
     *
     * @param name the name
     * @return the cashback plans
     */
    public static CashbackPlans parse(String name) {
        if (name.equalsIgnoreCase("spendingThreshold")) {
            return SPENDING_THRESHOLD;
        } else if (name.equalsIgnoreCase("nrOfTransactions")) {
            return NR_OF_TRANSACTIONS;
        }

        return NONE;
    }
}
