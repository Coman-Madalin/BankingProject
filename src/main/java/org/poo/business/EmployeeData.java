package org.poo.business;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeData {

    private String commerciant = null;
    private double amount;
    private int timestamp;

    public EmployeeData(double amount, int timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public EmployeeData(String commerciant, double amount, int timestamp) {
        this.commerciant = commerciant;
        this.amount = amount;
        this.timestamp = timestamp;
    }
}
