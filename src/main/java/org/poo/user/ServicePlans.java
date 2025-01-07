package org.poo.user;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public enum ServicePlans {
    STANDARD(1, 0.02),
    STUDENT(1, 0),
    SILVER(2, 0.01),
    GOLD(3, 0);

    private static final Map<List<Integer>, Integer> RANKS_TO_FEE = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(List.of(1, 2), 100),
            new AbstractMap.SimpleEntry<>(List.of(1, 3), 350),
            new AbstractMap.SimpleEntry<>(List.of(2, 3), 250)
    );


    private final int rank;
    private final double commission;

    ServicePlans(int rank, double commission) {
        this.rank = rank;
        this.commission = commission;
    }

    public static ServicePlans parse(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        return ServicePlans.valueOf(input.toUpperCase());
    }

    public double getCommission(double amount) {
        return amount * this.commission;
    }

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
