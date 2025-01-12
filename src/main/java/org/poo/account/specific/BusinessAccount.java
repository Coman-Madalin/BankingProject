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

/**
 * The type Business account.
 */
@Setter
@Getter
public class BusinessAccount extends BaseAccount {
    private List<Employee> employees = new ArrayList<>();
    private List<Employee> managers = new ArrayList<>();
    private double spendingLimit;
    private double depositLimit;

    /**
     * Instantiates a new Business account.
     *
     * @param currency the currency
     * @param user     the user
     */
    public BusinessAccount(String currency, User user) {
        super(currency, user);
        this.setType("business");
        double limitsValue = Input.getInstance().getExchanges().convertCurrency(500, "RON",
                currency);

        spendingLimit = limitsValue;
        depositLimit = limitsValue;
    }

    /**
     * Add employee.
     *
     * @param user the user
     * @param role the role
     */
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
     * Gets employee by email and role.
     * If the parameter role is NULL, it will search all available roles for the employee.
     *
     * @param email the email of the employee
     * @param role  the role of the employee (can be NULL to search all roles)
     * @return the employee by email and role, or NULL if not found
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

    /**
     * Make deposit.
     *
     * @param email     the email
     * @param amount    the amount
     * @param timestamp the timestamp
     */
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

    /**
     * Gets commerciant data.
     *
     * @return the commerciant data
     */
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
