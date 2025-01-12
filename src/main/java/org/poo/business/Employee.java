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
    public Employee(final User user, final String role) {
        this.user = user;
        this.role = role;
    }

    /**
     * Add deposit.
     *
     * @param amount    the amount
     * @param timestamp the timestamp
     */
    public void addDeposit(final double amount, final int timestamp) {
        depositedData.add(new EmployeeData(amount, timestamp));
    }

    /**
     * Add spending.
     *
     * @param commerciant the commerciant
     * @param amount      the amount
     * @param timestamp   the timestamp
     */
    public void addSpending(final String commerciant, final double amount, final int timestamp) {
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
    public double getTotalDepositedAmount(final int startTimestamp, final int endTimestamp) {
        double total = 0;
        for (final EmployeeData deposit : depositedData) {
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
    public double getTotalSpendAmount(final int startTimestamp, final int endTimestamp) {
        double total = 0;
        for (final EmployeeData spend : spendData) {
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
