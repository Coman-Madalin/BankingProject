package org.poo.account.specific;

import lombok.Getter;
import lombok.Setter;
import org.poo.account.BaseAccount;
import org.poo.business.CommerciantReportData;
import org.poo.business.Employee;
import org.poo.business.EmployeeData;
import org.poo.input.Input;
import org.poo.user.User;

import java.util.*;

import static org.poo.input.Input.printLog;

@Setter
@Getter
public class BusinessAccount extends BaseAccount {
    private List<Employee> employees = new ArrayList<>();
    private List<Employee> managers = new ArrayList<>();
    private double spendingLimit;
    private double depositLimit;

    public BusinessAccount(String currency, User user) {
        super(currency, user);
        this.setType("business");
        double limitsValue = Input.getInstance().getExchanges().convertCurrency(500, "RON",
                currency);

        spendingLimit = limitsValue;
        depositLimit = limitsValue;
    }

    public void addEmployee(User user, String role) {
        // TODO: Check that the employee is not already added
        if (getUser().getEmail().equalsIgnoreCase(user.getEmail())) {
            return;
        }

        switch (role) {
            case "employee" -> employees.add(new Employee(user, "employee"));
            case "manager" -> managers.add(new Employee(user, "manager"));
        }
    }

    /**
     * If parameter role is NULL, we will search all available roles for the employee
     */
    public Employee getEmployeeByEmailAndRole(String email, String role) {
        if (getUser().getEmail().equals(email)) {
            return new Employee(null, "owner");
        }

        if (role == null || role.equals("employee"))
            for (Employee employee : employees) {
                if (employee.getUser().getEmail().equals(email)) {
                    return employee;
                }
            }

        if (role == null || role.equals("manager"))
            for (Employee manager : managers) {
                if (manager.getUser().getEmail().equals(email)) {
                    return manager;
                }
            }

        return null;
    }

    public void makeDeposit(String email, double amount, int timestamp) {
        Employee employee = getEmployeeByEmailAndRole(email, null);

        if (employee == null) {
            //TODO: employee not found
            return;
        }

        if (employee.getRole().equalsIgnoreCase("employee") && amount > depositLimit) {
            return;
        }

        this.increaseBalance(amount);
        printLog("AddFunds:business", timestamp, amount, getBalance(), getIban());

        if (employee.getUser() == null) {
            //TODO: The CEO is making this command
            return;
        }
        employee.addDeposit(amount, timestamp);
    }

    private Set<String> getAllCommerciants() {
        Set<String> commerciants = new HashSet<>();

        for (Employee manager : managers) {
            for (EmployeeData payment : manager.getSpendData()) {
                commerciants.add(payment.getCommerciant());
            }
        }

        for (Employee employee : employees) {
            for (EmployeeData payment : employee.getSpendData()) {
                commerciants.add(payment.getCommerciant());
            }
        }

        return commerciants;
    }

    public List<CommerciantReportData> getCommerciantData() {
        List<CommerciantReportData> commerciantReportDataList = new ArrayList<>();
        Set<String> commerciants = getAllCommerciants();

        for (String commerciant : commerciants) {
            CommerciantReportData commerciantReportData = new CommerciantReportData();
            commerciantReportData.setCommerciant(commerciant);

            //TODO: remove the duplicate code
            for (Employee manager : managers) {
                for (EmployeeData payment : manager.getSpendData()) {
                    if (!payment.getCommerciant().equalsIgnoreCase(commerciant)) {
                        continue;
                    }

                    commerciantReportData.increaseTotalSpend(payment.getAmount());
                    commerciantReportData.addToList(manager.getUsername(), manager.getRole());
                }
            }

            for (Employee employee : employees) {
                for (EmployeeData payment : employee.getSpendData()) {
                    if (!payment.getCommerciant().equalsIgnoreCase(commerciant)) {
                        continue;
                    }

                    commerciantReportData.increaseTotalSpend(payment.getAmount());
                    commerciantReportData.addToList(employee.getUsername(), employee.getRole());
                }
            }

            commerciantReportData.postProcessing();
            commerciantReportDataList.add(commerciantReportData);
        }

        Collections.sort(commerciantReportDataList);
        return commerciantReportDataList;
    }

    @Override
    public boolean isValidEmail(String email) {
        if (getUser().getEmail().equalsIgnoreCase(email))
            return true;

        for (Employee manager : managers) {
            if (manager.getUser().getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        for (Employee employee : employees) {
            if (employee.getUser().getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }

        return false;
    }
}
