package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.specific.PlanUpgradeTransaction;
import org.poo.user.Account;
import org.poo.user.ServicePlans;
import org.poo.user.User;

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

        Account accountUser = Input.getInstance().getUsers().getAccountByIBAN(account);
        accountUser.getTransactionsHistory().add(new PlanUpgradeTransaction(
                "Upgrade plan", getTimestamp(), newPlanType, account
        ));

        ServicePlans newServicePlan = ServicePlans.parse(newPlanType);

        Integer fee = user.getServicePlan().canUpgrade(newServicePlan);
        if (fee == null) {
            System.out.println("Error parsing the new plan");
            return;
        }

        if (fee > 0) {
            final double accountCurrencyAmount = Input.getInstance().getExchanges()
                    .convertCurrency(fee, "RON", accountUser.getCurrency());

            if (!accountUser.hasEnoughBalance(accountCurrencyAmount)) {
                // TODO: user doesn't have enough money to upgrade
                return;
            }

            accountUser.decreaseBalance(accountCurrencyAmount);
            user.setServicePlan(newServicePlan);
        }

    }
}
