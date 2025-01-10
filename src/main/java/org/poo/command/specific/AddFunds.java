package org.poo.command.specific;

import org.poo.account.BaseAccount;
import org.poo.account.specific.BusinessAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;

/**
 * The type Add funds.
 */
public final class AddFunds extends BaseCommand {
    private String email;
    private String account;
    private int amount;

    /**
     * Instantiates a new Add funds.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public AddFunds(final String command, final int timestamp) {
        super(command, timestamp);
    }

    public void handleBusinessAccount(BusinessAccount account) {
        account.makeDeposit(email, amount, getTimestamp());
    }

    @Override
    public void execute() {
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
    }
}
