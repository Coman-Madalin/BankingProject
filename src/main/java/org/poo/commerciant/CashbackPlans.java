package org.poo.commerciant;

public enum CashbackPlans {
    NR_OF_TRANSACTIONS,
    SPENDING_THRESHOLD,
    NONE;

    public static CashbackPlans parse(String name) {
        if (name.equalsIgnoreCase("spendingThreshold")) {
            return SPENDING_THRESHOLD;
        } else if (name.equalsIgnoreCase("nrOfTransactions")) {
            return NR_OF_TRANSACTIONS;
        }
        return NONE;
    }
}
