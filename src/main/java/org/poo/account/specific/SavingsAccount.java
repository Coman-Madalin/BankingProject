package org.poo.account.specific;

import lombok.Getter;
import lombok.Setter;
import org.poo.account.BaseAccount;
import org.poo.user.User;

/**
 * The type Savings account.
 */
@Getter
@Setter
public class SavingsAccount extends BaseAccount {
    private double interestRate;

    /**
     * Instantiates a new Savings account.
     *
     * @param currency     the currency
     * @param user         the user
     * @param interestRate the interest rate
     */
    public SavingsAccount(final String currency, final User user, final double interestRate) {
        super(currency, user);
        this.interestRate = interestRate;
        this.setType("savings");
    }
}
