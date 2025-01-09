package org.poo.account.specific;

import lombok.Getter;
import lombok.Setter;
import org.poo.account.BaseAccount;
import org.poo.user.User;

@Getter
@Setter
public class SavingsAccount extends BaseAccount {
    private double interestRate;

    public SavingsAccount(String currency, User user, double interestRate) {
        super(currency, user);
        this.interestRate = interestRate;
        this.setType("savings");
    }
}
