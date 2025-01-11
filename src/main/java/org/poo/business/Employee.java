package org.poo.business;

import lombok.Getter;
import lombok.Setter;
import org.poo.user.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Employee {
    private List<EmployeeData> depositedData = new ArrayList<>();
    private List<EmployeeData> spendData = new ArrayList<>();

    private String role;
    private User user;

    public Employee(User user, String role) {
        this.user = user;
        this.role = role;
    }

    public void addDeposit(double amount, int timestamp) {
        depositedData.add(new EmployeeData(amount, timestamp));
    }

    public void addSpending(String commerciant, double amount, int timestamp) {
        spendData.add(new EmployeeData(commerciant, amount, timestamp));
    }

    public String getUsername() {
        return getUser().getLastName() + " " + getUser().getFirstName();
    }

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
