package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.account.BaseAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.transactions.specific.PlanUpgradeTransaction;
import org.poo.user.ServicePlans;
import org.poo.user.User;

import static org.poo.input.Input.printLog;

public class UpgradePlan extends BaseCommand {
    private String account;
    private String newPlanType;

    /**
     * Instantiates a new Base command.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public UpgradePlan(String command, int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        User user = Input.getInstance().getUsers().getUserByIBAN(account);

        BaseAccount accountUser = Input.getInstance().getUsers().getAccountByIBAN(account);
        if (accountUser == null) {
            JsonObject outputJson = new JsonObject();
            outputJson.addProperty("description", "Account not found");
            outputJson.addProperty("timestamp", getTimestamp());
            setOutput(outputJson.toString());
            return;
        }

        ServicePlans newServicePlan = ServicePlans.parse(newPlanType);

        if (user.getServicePlan() == newServicePlan) {
            accountUser.getTransactionsHistory().add(new BaseTransaction(
                    "The user already has the " + newServicePlan.toString().toLowerCase() + " plan.",
                    getTimestamp()
            ));
            return;
        }

        Integer fee = user.getServicePlan().canUpgrade(newServicePlan);
        if (fee == null) {
            System.out.println("Error parsing the new plan");
            return;
        }

        if (fee > 0) {
            final double accountCurrencyAmount = Input.getInstance().getExchanges()
                    .convertCurrency(fee, "RON", accountUser.getCurrency());

            if (!accountUser.hasEnoughBalance(accountCurrencyAmount)) {
                accountUser.getTransactionsHistory().add(new BaseTransaction(getTimestamp()));
                return;
            }

            accountUser.decreaseBalance(accountCurrencyAmount);
            user.setServicePlan(newServicePlan);
            accountUser.getTransactionsHistory().add(new PlanUpgradeTransaction(
                    "Upgrade plan", getTimestamp(), newPlanType, account
            ));

            printLog("UpgradePlan:" + newPlanType, getTimestamp(),
                    accountCurrencyAmount, accountUser.getBalance(), accountUser.getUser().getEmail());

        } else {
            System.out.printf("Can't upgrade %s from %s -> %s\n", accountUser.getIban(),
                    accountUser.getUser().getServicePlan().toString(), newPlanType);
        }

    }
}
