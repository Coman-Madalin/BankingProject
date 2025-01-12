package org.poo.business;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Employee data.
 */
@Getter
@Setter
public class EmployeeData {
    private String commerciant = null;
    private double amount;
    private int timestamp;

    /**
     * Instantiates a new Employee data.
     *
     * @param amount    the amount
     * @param timestamp the timestamp
     */
    public EmployeeData(final double amount, final int timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    /**
     * Instantiates a new Employee data.
     *
     * @param commerciant the commerciant
     * @param amount      the amount
     * @param timestamp   the timestamp
     */
    public EmployeeData(final String commerciant, final double amount, final int timestamp) {
        this.commerciant = commerciant;
        this.amount = amount;
        this.timestamp = timestamp;
    }
}
