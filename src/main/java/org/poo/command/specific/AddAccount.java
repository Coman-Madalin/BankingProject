package org.poo.command.specific;

import org.poo.account.BaseAccount;
import org.poo.account.specific.BusinessAccount;
import org.poo.account.specific.SavingsAccount;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.transactions.BaseTransaction;
import org.poo.user.User;

import static org.poo.input.Input.printLog;

/**
 * The type Add account.
 */
public final class AddAccount extends BaseCommand {
    private final String email;
    private final String currency;
    private final String accountType;
    private final double interestRate;

    /**
     * Instantiates a new Add account.
     *
     * @param command      the command
     * @param timestamp    the timestamp
     * @param email        the email
     * @param currency     the currency
     * @param accountType  the account type
     * @param interestRate the interest rate
     */
    public AddAccount(final String command, final int timestamp, final String email,
                      final String currency, final String accountType, final double interestRate) {
        super(command, timestamp);
        this.email = email;
        this.currency = currency;
        this.accountType = accountType;
        this.interestRate = interestRate;
    }

    @Override
    public void execute() {
        final Input input = Input.getInstance();
        final User user = input.getUsers().getUserByEmail(email);

        BaseAccount account = null;

        switch (accountType) {
            case "classic" -> account = new BaseAccount(currency, user);
            case "savings" -> account = new SavingsAccount(currency, user, interestRate);
            case "business" -> account = new BusinessAccount(currency, user);
            default -> System.out.println("UNSUPPORTED ACCOUNT TYPE");
        }

        if (account == null) {
            return;
        }

        printLog("AddAccount:" + accountType, getTimestamp(), 0, 0, account.getIban());


        user.getAccounts().add(account);
        account.getTransactionsHistory().add(new BaseTransaction("New account created",
                getTimestamp()));

    }
}
