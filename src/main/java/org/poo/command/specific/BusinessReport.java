package org.poo.command.specific;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.poo.account.specific.BusinessAccount;
import org.poo.business.CommerciantReportData;
import org.poo.business.Employee;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.json.JsonUtils;

import java.util.List;

/**
 * The type Business report.
 */
public class BusinessReport extends BaseCommand {
    private int startTimestamp;
    private int endTimestamp;
    private String account;
    private String type;

    /**
     * Instantiates a new Business report.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public BusinessReport(String command, int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        switch (type) {
            case "transaction" -> generateTransactionReport();
            case "commerciant" -> generateCommerciantReport();
            default -> throw new RuntimeException("Business report type not supported!");
        }
    }

    private void generateCommerciantReport() {
        BusinessAccount businessAccount = (BusinessAccount) Input.getInstance().getUsers()
                .getAccountByIBAN(account);

        if (businessAccount == null) {
            // TODO: Account not found
            return;
        }

        final JsonObject outputJson = new JsonObject();
        outputJson.addProperty("IBAN", businessAccount.getIban());
        outputJson.addProperty("balance", businessAccount.getBalance());
        outputJson.addProperty("currency", businessAccount.getCurrency());
        outputJson.addProperty("spending limit", businessAccount.getSpendingLimit());
        outputJson.addProperty("deposit limit", businessAccount.getDepositLimit());
        outputJson.addProperty("statistics type", type);

        List<CommerciantReportData> commerciantReportDataList =
                businessAccount.getCommerciantData();

        outputJson.add("commerciants", JsonUtils.getGSON().toJsonTree(commerciantReportDataList));

        setOutput(outputJson.toString());
    }

    private void generateTransactionReport() {
        BusinessAccount businessAccount = (BusinessAccount) Input.getInstance().getUsers()
                .getAccountByIBAN(account);

        if (businessAccount == null) {
            // TODO: Account not found
            return;
        }

        final JsonObject outputJson = new JsonObject();
        outputJson.addProperty("IBAN", businessAccount.getIban());
        outputJson.addProperty("balance", businessAccount.getBalance());
        outputJson.addProperty("currency", businessAccount.getCurrency());
        outputJson.addProperty("spending limit", businessAccount.getSpendingLimit());
        outputJson.addProperty("deposit limit", businessAccount.getDepositLimit());
        outputJson.addProperty("statistics type", type);

        double totalSpend = 0;
        double totalDeposited = 0;

        JsonArray jsonManagers = new JsonArray();
        for (Employee manager : businessAccount.getManagers()) {
            JsonObject jsonManager = new JsonObject();
            jsonManager.addProperty("username",
                    manager.getUser().getLastName() + " " + manager.getUser().getFirstName());
            double spend = manager.getTotalSpendAmount(startTimestamp, endTimestamp);
            double deposited = manager.getTotalDepositedAmount(startTimestamp, endTimestamp);

            jsonManager.addProperty("spent", spend);
            jsonManager.addProperty("deposited", deposited);
            jsonManagers.add(jsonManager);

            totalSpend += spend;
            totalDeposited += deposited;
        }

        outputJson.add("managers", jsonManagers);

        JsonArray jsonEmployees = new JsonArray();

        for (Employee employee : businessAccount.getEmployees()) {
            JsonObject jsonEmployee = new JsonObject();
            jsonEmployee.addProperty("username",
                    employee.getUser().getLastName() + " " + employee.getUser().getFirstName());
            double spend = employee.getTotalSpendAmount(startTimestamp, endTimestamp);
            double deposited = employee.getTotalDepositedAmount(startTimestamp, endTimestamp);

            jsonEmployee.addProperty("spent", spend);
            jsonEmployee.addProperty("deposited", deposited);
            jsonEmployees.add(jsonEmployee);

            totalSpend += spend;
            totalDeposited += deposited;
        }

        outputJson.add("employees", jsonEmployees);

        outputJson.addProperty("total spent", totalSpend);
        outputJson.addProperty("total deposited", totalDeposited);


        setOutput(outputJson.toString());
    }
}
