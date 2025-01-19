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
public final class BusinessAccount extends BaseAccount {
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
    public BusinessAccount(final String currency, final User user) {
        super(currency, user);
        this.setType("business");
        final double limitsValue = Input.getInstance().getExchanges().convertCurrency(500, "RON",
                currency);

        spendingLimit = limitsValue;
        depositLimit = limitsValue;
    }

    private boolean employeeAlreadyExists(final String email) {
        if (getUser().getEmail().equalsIgnoreCase(email)) {
            return true;
        }

        for (final Employee manager : managers) {
            if (manager.getUser().getEmail().equals(email)) {
                return true;
            }
        }

        for (final Employee employee : employees) {
            if (employee.getUser().getEmail().equals(email)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Add employee.
     *
     * @param user the user
     * @param role the role
     */
    public void addEmployee(final User user, final String role) {
        if (getUser().getEmail().equalsIgnoreCase(user.getEmail())) {
            return;
        }

        if (employeeAlreadyExists(user.getEmail())) {
            return;
        }

        switch (role) {
            case "employee" -> employees.add(new Employee(user, role));
            case "manager" -> managers.add(new Employee(user, role));
            default -> throw new RuntimeException("ROLE NOT FOUND FOR NEW EMPLOYEE");
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
    public Employee getEmployeeByEmailAndRole(final String email, final String role) {
        if (getUser().getEmail().equals(email)) {
            return new Employee(null, "owner");
        }

        if (role == null || role.equals("employee")) {
            for (final Employee employee : employees) {
                if (employee.getUser().getEmail().equals(email)) {
                    return employee;
                }
            }
        }

        if (role == null || role.equals("manager")) {
            for (final Employee manager : managers) {
                if (manager.getUser().getEmail().equals(email)) {
                    return manager;
                }
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
    public void makeDeposit(final String email, final double amount, final int timestamp) {
        final Employee employee = getEmployeeByEmailAndRole(email, null);

        if (employee == null) {
            return;
        }

        if (employee.getRole().equalsIgnoreCase("employee") && amount > depositLimit) {
            return;
        }

        this.increaseBalance(amount);
        printLog("AddFunds:business", timestamp, amount, getBalance(), getIban());

        if (employee.getUser() == null) {
            return;
        }

        employee.addDeposit(amount, timestamp);
    }

    private Set<String> getAllCommerciants() {
        final Set<String> commerciants = new HashSet<>();

        for (final Employee manager : managers) {
            for (final EmployeeData payment : manager.getSpendData()) {
                commerciants.add(payment.getCommerciant());
            }
        }

        for (final Employee employee : employees) {
            for (final EmployeeData payment : employee.getSpendData()) {
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
        final List<CommerciantReportData> commerciantReportDataList = new ArrayList<>();
        final Set<String> commerciants = getAllCommerciants();

        for (final String commerciant : commerciants) {
            final CommerciantReportData commerciantReportData = new CommerciantReportData();
            commerciantReportData.setCommerciant(commerciant);

            for (final Employee manager : managers) {
                processForCommerciantData(commerciant, manager, commerciantReportData);
            }

            for (final Employee employee : employees) {
                processForCommerciantData(commerciant, employee, commerciantReportData);
            }

            commerciantReportData.postProcessing();
            commerciantReportDataList.add(commerciantReportData);
        }

        Collections.sort(commerciantReportDataList);
        return commerciantReportDataList;
    }

    private void processForCommerciantData(final String commerciant, final Employee employee,
                                           final CommerciantReportData commerciantReportData) {
        for (final EmployeeData payment : employee.getSpendData()) {
            if (!payment.getCommerciant().equalsIgnoreCase(commerciant)) {
                continue;
            }

            commerciantReportData.increaseTotalSpend(payment.getAmount());
            commerciantReportData.addToList(employee.getUsername(), employee.getRole());
        }
    }

    @Override
    public boolean isValidEmail(final String email) {
        return !(getEmployeeRole(email) == null);
    }

    /**
     * Gets employee role.
     *
     * @param email the email
     * @return the employee role
     */
    public String getEmployeeRole(final String email) {
        if (getUser().getEmail().equalsIgnoreCase(email)) {
            return "owner";
        }

        for (final Employee manager : managers) {
            if (manager.getUser().getEmail().equalsIgnoreCase(email)) {
                return manager.getRole();
            }
        }
        for (final Employee employee : employees) {
            if (employee.getUser().getEmail().equalsIgnoreCase(email)) {
                return employee.getRole();
            }
        }

        return null;
    }
}
