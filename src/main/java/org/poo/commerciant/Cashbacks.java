package org.poo.commerciant;

import lombok.Getter;

import java.util.AbstractMap;
import java.util.Map;

/**
 * The enum Cashback.
 */
public enum Cashbacks {
    /**
     * Food cashback.
     */
    FOOD(0.02),
    /**
     * Clothes cashback.
     */
    CLOTHES(0.05),
    /**
     * Tech cashback.
     */
    TECH(0.1),
    /**
     * No cashback.
     * This is a placeholder for cases where no cashback is applicable.
     */
    NONE(0.0);

    private static final Map<Integer, Cashbacks> ORDER_TO_CASHBACK = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(0, Cashbacks.FOOD),
            new AbstractMap.SimpleEntry<>(1, Cashbacks.CLOTHES),
            new AbstractMap.SimpleEntry<>(2, Cashbacks.TECH)
    );

    private static final int[] THRESHOLDS = {2, 5, 10};
    @Getter
    private final double discount;

    Cashbacks(final double discount) {
        this.discount = discount;
    }

    /**
     * Update cash back cashbacks.
     *
     * @param nrOfTransactions the nr of transactions
     * @return the cashbacks
     */
    public Cashbacks updateCashBack(final int nrOfTransactions) {
        for (int i = 0; i < THRESHOLDS.length; i++) {
            if (THRESHOLDS[i] == nrOfTransactions) {
                return ORDER_TO_CASHBACK.get(i);
            }
        }

        return NONE;
    }
}
