package org.poo.command.specific;

import org.poo.account.specific.BusinessAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;

public class ChangeDepositLimit extends BaseCommand {
    private String email;
    private String account;
    private double amount;

    /**
     * Instantiates a new Base command.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public ChangeDepositLimit(String command, int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        BusinessAccount businessAccount = (BusinessAccount) Input.getInstance().getUsers()
                .getAccountByIBAN(account);

        if (!businessAccount.getUser().getEmail().equals(email)) {
            //TODO: only the CEO can change limits
            return;
        }

        businessAccount.setDepositLimit(amount);
    }
}
