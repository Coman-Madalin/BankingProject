package org.poo.business;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Commerciant report data.
 */
@NoArgsConstructor
@Getter
@Setter
public class CommerciantReportData implements Comparable<CommerciantReportData> {
    private String commerciant;
    @SerializedName("total received")
    private double totalSpend;
    private List<String> managers = new ArrayList<>();
    private List<String> employees = new ArrayList<>();

    /**
     * Increase total spend.
     *
     * @param amount the amount
     */
    public void increaseTotalSpend(double amount) {
        totalSpend += amount;
    }

    /**
     * Add to list.
     *
     * @param name the name
     * @param role the role
     */
    public void addToList(String name, String role) {
        switch (role) {
            case "manager" -> managers.add(name);
            case "employee" -> employees.add(name);
            default -> throw new RuntimeException("Unknown role");
        }
    }

    /**
     * Post processing.
     */
    public void postProcessing() {
        Collections.sort(managers);
        Collections.sort(employees);
    }

    @Override
    public int compareTo(CommerciantReportData o) {
        return commerciant.compareTo(o.commerciant);
    }
}
