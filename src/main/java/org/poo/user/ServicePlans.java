package org.poo.user;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * The enum Service plans.
 */
public enum ServicePlans {
    /**
     * The Standard.
     */
    STANDARD(1, 0.002, new double[]{0.001, 0.002, 0.0025}),
    /**
     * The Student.
     */
    STUDENT(1, 0, new double[]{0.001, 0.002, 0.0025}),
    /**
     * The Silver.
     */
    SILVER(2, 0.001, new double[]{0.003, 0.004, 0.005}),
    /**
     * The Gold.
     */
    GOLD(3, 0, new double[]{0.005, 0.0055, 0.007});

    private static final Map<List<Integer>, Integer> RANKS_TO_FEE = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(List.of(1, 2), 100),
            new AbstractMap.SimpleEntry<>(List.of(1, 3), 350),
            new AbstractMap.SimpleEntry<>(List.of(2, 3), 250)
    );


    private final int rank;
    private final double commission;
    private final double[] spendingDiscount;

    ServicePlans(int rank, double commission, double[] spendingDiscount) {
        this.rank = rank;
        this.commission = commission;
        this.spendingDiscount = spendingDiscount;
    }

    /**
     * Parse service plans.
     *
     * @param input the input
     * @return the service plans
     */
    public static ServicePlans parse(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        return ServicePlans.valueOf(input.toUpperCase());
    }

    /**
     * Gets spending discount.
     *
     * @param index the index
     * @return the spending discount
     */
    public double getSpendingDiscount(int index) {
        return spendingDiscount[index];
    }

    /**
     * Gets commission.
     *
     * @param amount the amount
     * @return the commission
     */
    public double getCommission(double amount) {
        if (this == SILVER && amount < 500) {
            return 0;
        }
        return amount * this.commission;
    }

    /**
     * Can upgrade integer.
     *
     * @param plan the plan
     * @return the integer
     */
    public Integer canUpgrade(ServicePlans plan) {
        if (plan == null) {
            return null;
        }

        if (this.rank < plan.rank) {
            return RANKS_TO_FEE.get(List.of(this.rank, plan.rank));
        }
        return -1;
    }
}
