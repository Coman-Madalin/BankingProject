package org.poo.account.specific;

import lombok.Getter;
import lombok.Setter;
import org.poo.account.BaseAccount;
import org.poo.business.EmployeeAccount;
import org.poo.user.User;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class BusinessAccount extends BaseAccount {
    private List<EmployeeAccount> employees = new ArrayList<>();
    private List<EmployeeAccount> managers = new ArrayList<>();
    private double spendingLimit = 500;
    private double depositLimit = 500;

    public BusinessAccount(String currency, User user) {
        super(currency, user);
        this.setType("business");
    }

    public void addEmployee(User user, String role) {
        switch (role) {
            case "employee" -> employees.add(new EmployeeAccount(user, "employee"));
            case "manager" -> managers.add(new EmployeeAccount(user, "manager"));
        }

    }

    /**
     * If parameter role is NULL, we will search all available roles for the employee
     */
    public EmployeeAccount getEmployeeByEmailAndRole(String email, String role) {
        if (getUser().getEmail().equals(email)) {
            return new EmployeeAccount(null, "owner");
        }

        if (role == null || role.equals("employee"))
            for (EmployeeAccount employee : employees) {
                if (employee.getUser().getEmail().equals(email)) {
                    return employee;
                }
            }

        if (role == null || role.equals("manager"))
            for (EmployeeAccount manager : managers) {
                if (manager.getUser().getEmail().equals(email)) {
                    return manager;
                }
            }

        return null;
    }

    public void makeDeposit(String email, double amount, int timestamp) {
        EmployeeAccount employeeAccount = getEmployeeByEmailAndRole(email, "manager");

        if (employeeAccount == null) {
            //TODO: employee not found
            return;
        }

        this.increaseBalance(amount);
        printLog("Addfunds", timestamp, amount);

        if (employeeAccount.getUser() == null) {
            //TODO: The CEO is making this command
            return;
        }
        employeeAccount.addDeposit(amount, timestamp);
    }


    public void printLog(String action, int timestamp, double amount) {
        System.out.printf("%d        %s          %f          %f%n", timestamp, action
                , amount, getBalance());
    }
}
