package org.poo.business;

import lombok.Getter;
import lombok.Setter;
import org.poo.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Employee.
 */
@Getter
@Setter
public class Employee {
    private List<EmployeeData> depositedData = new ArrayList<>();
    private List<EmployeeData> spendData = new ArrayList<>();

    private String role;
    private User user;

    /**
     * Instantiates a new Employee.
     *
     * @param user the user
     * @param role the role
     */
    public Employee(User user, String role) {
        this.user = user;
        this.role = role;
    }

    /**
     * Add deposit.
     *
     * @param amount    the amount
     * @param timestamp the timestamp
     */
    public void addDeposit(double amount, int timestamp) {
        depositedData.add(new EmployeeData(amount, timestamp));
    }

    /**
     * Add spending.
     *
     * @param commerciant the commerciant
     * @param amount      the amount
     * @param timestamp   the timestamp
     */
    public void addSpending(String commerciant, double amount, int timestamp) {
        spendData.add(new EmployeeData(commerciant, amount, timestamp));
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return getUser().getLastName() + " " + getUser().getFirstName();
    }

    /**
     * Gets total deposited amount.
     *
     * @param startTimestamp the start timestamp
     * @param endTimestamp   the end timestamp
     * @return the total deposited amount
     */
    public double getTotalDepositedAmount(int startTimestamp, int endTimestamp) {
        double total = 0;
        for (EmployeeData deposit : depositedData) {
            if (startTimestamp > deposit.getTimestamp()) {
                continue;
            }

            if (deposit.getTimestamp() > endTimestamp) {
                break;
            }

            total += deposit.getAmount();
        }

        return total;
    }


    /**
     * Gets total spend amount.
     *
     * @param startTimestamp the start timestamp
     * @param endTimestamp   the end timestamp
     * @return the total spend amount
     */
    public double getTotalSpendAmount(int startTimestamp, int endTimestamp) {
        double total = 0;
        for (EmployeeData spend : spendData) {
            if (startTimestamp > spend.getTimestamp()) {
                continue;
            }

            if (spend.getTimestamp() > endTimestamp) {
                break;
            }

            total += spend.getAmount();
        }

        return total;
    }
}
