package org.poo.command.specific;

import org.poo.account.specific.BusinessAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.user.User;

/**
 * The type Add new business associate.
 */
public class AddNewBusinessAssociate extends BaseCommand {
    private String account;
    private String role;
    private String email;

    /**
     * Instantiates a new Add new business associate.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public AddNewBusinessAssociate(String command, int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute() {
        BusinessAccount businessAccount = (BusinessAccount) Input.getInstance().getUsers()
                .getAccountByIBAN(account);

        if (businessAccount == null) {
            //TODO: business account not found
            return;
        }

        User employee = Input.getInstance().getUsers().getUserByEmail(email);

        businessAccount.addEmployee(employee, role);
    }
}
