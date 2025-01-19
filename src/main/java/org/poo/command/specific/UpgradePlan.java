package org.poo.command.specific;

import com.google.gson.JsonObject;
import org.poo.account.BaseAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.transactions.specific.PlanUpgradeTransaction;
import org.poo.user.ServicePlans;
import org.poo.user.User;

/**
 * The type Upgrade plan.
 */
public final class UpgradePlan extends BaseCommand {
    private String account;
    private String newPlanType;

    /**
     * Instantiates a new Upgrade plan.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public UpgradePlan(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        final User user = Input.getInstance().getUsers().getUserByIBAN(account);

        final BaseAccount accountUser = Input.getInstance().getUsers().getAccountByIBAN(account);
        if (accountUser == null) {
            final JsonObject outputJson = new JsonObject();
            outputJson.addProperty("description", "Account not found");
            outputJson.addProperty("timestamp", getTimestamp());
            setOutput(outputJson.toString());
            return;
        }

        final ServicePlans newServicePlan = ServicePlans.parse(newPlanType);

        if (user.getServicePlan() == newServicePlan) {
            accountUser.getTransactionsHistory().add(new BaseTransaction(
                    "The user already has the " + newServicePlan.toString().toLowerCase()
                            + " plan.",
                    getTimestamp()
            ));
            return;
        }

        final Integer fee = user.getServicePlan().canUpgrade(newServicePlan);
        if (fee == null) {
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
        }
    }
}
