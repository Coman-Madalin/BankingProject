package org.poo.command.specific;

import org.poo.account.specific.BusinessAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.user.User;

/**
 * The type Add new business associate.
 */
public final class AddNewBusinessAssociate extends BaseCommand {
    private String account;
    private String role;
    private String email;

    /**
     * Instantiates a new Add new business associate.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public AddNewBusinessAssociate(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        final BusinessAccount businessAccount = (BusinessAccount) Input.getInstance().getUsers()
                .getAccountByIBAN(account);

        final User employee = Input.getInstance().getUsers().getUserByEmail(email);

        businessAccount.addEmployee(employee, role);
    }
}
