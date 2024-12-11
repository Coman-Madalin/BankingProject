package org.poo.user;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static org.poo.utils.Utils.generateIBAN;

@Setter
@Getter
public class Account {
    private String IBAN;
    private double balance = 0;
    private double minBalance = 0;
    private String currency;
    private String type;
    private List<Card> cards = new ArrayList<>();

    public Account(final String currency, final String type) {
        this.currency = currency;
        this.type = type;
        this.IBAN = generateIBAN();
    }

    public void increaseBalance(final double amount) {
        balance += amount;
    }

    public void decreaseBalance(final double amount) {
        balance -= amount;
        if (balance < minBalance)
            balance = minBalance;
    }

    public Card getCardByCardNumber(final String cardNumber) {
        for (final Card card : this.getCards()) {
            if (card.getCardNumber().equals(cardNumber)) {
                return card;
            }
        }

        return null;
    }
}
