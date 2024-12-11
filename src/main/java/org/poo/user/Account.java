package org.poo.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class Account {
    private String IBAN;
    private double balance = 0;
    private double minBalance = 0;
    private String currency;
    private String type;
    private List<Card> cards = new ArrayList<>();

    public void increaseBalance(final double amount) {
        balance += amount;
    }

    public void decreaseBalance(final double amount) {
        balance -= amount;
        if (balance < minBalance)
            balance = minBalance;
    }
}
