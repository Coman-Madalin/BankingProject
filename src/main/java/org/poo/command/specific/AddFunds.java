package org.poo.command.specific;

import org.poo.account.BaseAccount;
import org.poo.account.specific.BusinessAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;

import static org.poo.input.Input.printLog;

/**
 * The type Add funds.
 */
public final class AddFunds extends BaseCommand {
    private String email;
    private String account;
    private double amount;

    /**
     * Instantiates a new Add funds.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public AddFunds(final String command, final int timestamp) {
        super(command, timestamp);
    }

    /**
     * Handle business account.
     *
     * @param account the account
     */
    public void handleBusinessAccount(BusinessAccount account) {
        account.makeDeposit(email, amount, getTimestamp());
    }

    @Override
    public void execute() {
        if (getTimestamp() == 593) {
            System.out.println("DADAD");
        }

        final Input input = Input.getInstance();
        final BaseAccount baseAccount = input.getUsers().getAccountByIBAN(account);

        if (baseAccount == null) {
            //TODO: account not found
            return;
        }

        if (baseAccount.getType().equals("business")) {
            handleBusinessAccount((BusinessAccount) baseAccount);
            return;
        }

        baseAccount.increaseBalance(amount);
        printLog("AddFunds:classic", getTimestamp(), amount, baseAccount.getBalance(),
                baseAccount.getIban());

    }
}
