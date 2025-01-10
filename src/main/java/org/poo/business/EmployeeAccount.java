package org.poo.business;

import lombok.Getter;
import lombok.Setter;
import org.poo.user.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EmployeeAccount {
    private List<EmployeeTransactions> depositedAmount = new ArrayList<>();
    private List<EmployeeTransactions> spendAmount = new ArrayList<>();

    private String role;
    private User user;

    public EmployeeAccount(User user, String role) {
        this.user = user;
        this.role = role;
    }

    public void addDeposit(double amount, int timestamp) {
        depositedAmount.add(new EmployeeTransactions(amount, timestamp));
    }

    public void addSpending(double amount, int timestamp) {
        spendAmount.add(new EmployeeTransactions(amount, timestamp));
    }

    public double getTotalDepositedAmount(int startTimestamp, int endTimestamp) {
        double total = 0;
        for (EmployeeTransactions deposit : depositedAmount) {
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
        for (EmployeeTransactions spend : spendAmount) {
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
