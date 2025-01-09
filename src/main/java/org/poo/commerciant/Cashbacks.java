package org.poo.commerciant;

import lombok.Getter;

import java.util.AbstractMap;
import java.util.Map;

public enum Cashbacks {
    FOOD(0.02),
    CLOTHES(0.05),
    TECH(0.1),
    NONE(0.0);

    private static final Map<Integer, Cashbacks> ORDER_TO_CASHBACK = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(0, Cashbacks.FOOD),
            new AbstractMap.SimpleEntry<>(1, Cashbacks.CLOTHES),
            new AbstractMap.SimpleEntry<>(2, Cashbacks.TECH)
    );

    private static final int[] threshholds = {2, 5, 10};
    @Getter
    private final double discount;

    Cashbacks(double discount) {
        this.discount = discount;
    }

    public Cashbacks updateCashBack(int nrOfTransactions) {
        for (int i = 0; i < threshholds.length; i++) {
            if (threshholds[i] == nrOfTransactions) {
                return ORDER_TO_CASHBACK.get(i);
            }
        }

        return NONE;
    }
}
