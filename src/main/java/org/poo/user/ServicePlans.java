package org.poo.user;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public enum ServicePlans {
    STANDARD(1),
    STUDENT(1),
    SILVER(2),
    GOLD(3);

    private static final Map<List<Integer>, Integer> RANKS_TO_FEE = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(List.of(1, 2), 100),
            new AbstractMap.SimpleEntry<>(List.of(1, 3), 350),
            new AbstractMap.SimpleEntry<>(List.of(2, 3), 250)
    );
    private final int rank;

    ServicePlans(int rank) {
        this.rank = rank;
    }

    public static ServicePlans parse(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        return ServicePlans.valueOf(input.toUpperCase());
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
