package org.poo.business;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CommerciantReportData implements Comparable<CommerciantReportData> {
    private String commerciant;
    private double totalReceived;
    private List<String> managers;
    private List<String> employees;

    public void increaseTotalReceived(double amount) {
        totalReceived += amount;
    }

    public void addToList(String name, String role) {
        switch (role) {
            case "manager" -> managers.add(name);
            case "employee" -> employees.add(name);
            default -> throw new RuntimeException("Unknown role");
        }
    }

    public void postProcessing() {
        Collections.sort(managers);
        Collections.sort(employees);
    }

    @Override
    public int compareTo(CommerciantReportData o) {
        return commerciant.compareTo(o.commerciant);
    }
}
