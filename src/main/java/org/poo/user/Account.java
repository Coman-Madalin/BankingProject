package org.poo.user;

import lombok.Getter;
import lombok.Setter;
import org.poo.transactions.BaseTransaction;

import java.util.ArrayList;
import java.util.List;

import static org.poo.utils.Utils.generateIBAN;

/**
 * The type Account.
 */
@Setter
@Getter
public class Account {
    private String iban;
    private double balance = 0;
    private double minBalance = 0;
    private String currency;
    private String type;
    private String alias = null;
    private List<Card> cards = new ArrayList<>();
    private List<BaseTransaction> transactionsHistory = new ArrayList<>();
    // TODO: maybe make a savings account since normal account doesn't have interestRate
    private double interestRate;

    /**
     * Instantiates a new Account.
     *
     * @param currency the currency
     * @param type     the type
     */
    public Account(final String currency, final String type) {
        this.currency = currency;
        this.type = type;
        this.iban = generateIBAN();
    }

    /**
     * Increase balance.
     *
     * @param amount the amount
     */
    public void increaseBalance(final double amount) {
        balance += amount;
    }

    /**
     * Has enough balance boolean.
     *
     * @param amount the amount
     * @return the boolean
     */
    public boolean hasEnoughBalance(final double amount) {
        final double oldBalance = balance;
        double balanceCopy = balance - amount;
        if (balanceCopy < 0) {
            balanceCopy = 0;
        }

        return amount - 1 < (oldBalance - balanceCopy) && (oldBalance - balanceCopy) < amount + 1;
    }

    /**
     * Decrease balance.
     *
     * @param amount the amount
     */
    public void decreaseBalance(final double amount) {
        balance -= amount;
        if (balance < 0) {
            balance = 0;
        }
    }

    /**
     * Gets card by card number.
     *
     * @param cardNumber the card number
     * @return the card by card number
     */
    public Card getCardByCardNumber(final String cardNumber) {
        for (final Card card : this.getCards()) {
            if (card.getCardNumber().equals(cardNumber)) {
                return card;
            }
        }

        return null;
    }
}
