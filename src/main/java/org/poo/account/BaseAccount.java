package org.poo.account;

import lombok.Getter;
import lombok.Setter;
import org.poo.commerciant.Cashbacks;
import org.poo.commerciant.Commerciant;
import org.poo.transactions.BaseTransaction;
import org.poo.user.Card;
import org.poo.user.Data;
import org.poo.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.poo.utils.Utils.generateIBAN;

/**
 * The type Account.
 */
@Setter
@Getter
public class BaseAccount {
    private String iban;
    private double balance = 0;
    private double minBalance = 0;
    private String currency;
    private String type = "classic";
    private String alias = null;
    private List<Card> cards = new ArrayList<>();
    private List<BaseTransaction> transactionsHistory = new ArrayList<>();
    private User user;

    @Setter
    private Cashbacks cashbackForTransactionsCount = Cashbacks.NONE;

    private HashMap<Commerciant, Data> COMMERCIANT_TO_DATA = new HashMap<>();

    public BaseAccount(final String currency, User user) {
        this.user = user;
        this.currency = currency;
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

    public double getSpendingDiscount(Commerciant commerciant, double amount) {
        Data data = COMMERCIANT_TO_DATA.get(commerciant);
        if (data == null) {
            data = new Data(0, 0);
        }

        int[] thresholds = {100, 300, 500};
        for (int i = thresholds.length - 1; i >= 0; i--) {
            if (data.getTotalSpend() + amount >= thresholds[i]) {
//            if (data.getTotalSpend() < thresholds[i] && data.getTotalSpend() + amount > thresholds[i]) {
                return user.getServicePlan().getSpendingDiscount(i);
            }
        }
        return 0;
    }

    public void addTransaction(Commerciant commerciant, double amount) {
        Data data = COMMERCIANT_TO_DATA.getOrDefault(commerciant, new Data(0, 0));
        data.addTransaction(amount);
        COMMERCIANT_TO_DATA.put(commerciant, data);
    }

    public double getDiscountForTransactionCount(String commerciantType) {
        if (Cashbacks.valueOf(commerciantType.toUpperCase()) == cashbackForTransactionsCount) {
            return cashbackForTransactionsCount.getDiscount();
        }
        return 0;
    }

    public void updateCashback(Commerciant commerciant) {
        Data data = COMMERCIANT_TO_DATA.get(commerciant);
        cashbackForTransactionsCount =
                cashbackForTransactionsCount.updateCashBack(data.getNrTransactions());
    }

    public void invalidateCashback() {
        cashbackForTransactionsCount = Cashbacks.NONE;
    }
}
