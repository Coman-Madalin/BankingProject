package org.poo.business;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeTransactions {
    private double amount;
    private int timestamp;

    public EmployeeTransactions(double amount, int timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }
}
