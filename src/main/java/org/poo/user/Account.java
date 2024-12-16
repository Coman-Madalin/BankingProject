package org.poo.user;

import lombok.Getter;
import lombok.Setter;
import org.poo.transactions.BaseTransaction;

import java.util.ArrayList;
import java.util.List;

import static org.poo.utils.Utils.generateCardNumber;
import static org.poo.utils.Utils.generateIBAN;

@Setter
@Getter
public class Account {
    private String IBAN;
    private double balance = 0;
    private double minBalance = 0;
    private String currency;
    private String type;
    private String alias = null;
    private List<Card> cards = new ArrayList<>();
    private List<BaseTransaction> transactionsHistory = new ArrayList<>();
    // TODO: maybe make a savings account since normal account doesn't have interestRate
    private double interestRate;

    public Account(final String currency, final String type) {
        this.currency = currency;
        this.type = type;
        this.IBAN = generateIBAN();
    }

    public void increaseBalance(final double amount) {
        balance += amount;
    }

    public boolean hasEnoughBalance(final double amount) {
        final double oldBalance = balance;
        double balanceCopy = balance - amount;
        if (balanceCopy < 0) {
            balanceCopy = 0;
        }

        return amount - 1 < (oldBalance - balanceCopy) && (oldBalance - balanceCopy) < amount + 1;
    }

    public void decreaseBalance(final double amount) {
        balance -= amount;
        if (balance < 0) {
            balance = 0;
        }
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
