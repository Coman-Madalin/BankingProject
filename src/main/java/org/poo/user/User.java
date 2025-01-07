package org.poo.user;

import lombok.Getter;
import lombok.Setter;
import org.poo.transactions.BaseTransaction;

import java.time.LocalDate;
import java.util.*;

/**
 * The type User.
 */
@Getter
public final class User {
    private final List<Account> accounts = new ArrayList<>();
    private String firstName;
    private String lastName;
    private String email;
    private final Statistics statistics = null;
    private String occupation;
    private int age;
    @Setter
    private ServicePlans servicePlan = ServicePlans.STANDARD;
    /**
     * DON'T use this, this is only for deserialization purposes, use age field instead
     */
    private String birthDate;

    /**
     * Gets account by alias.
     *
     * @param alias the alias
     * @return the account by alias
     */
    public Account getAccountByAlias(final String alias) {
        for (final Account account : accounts) {
            if (alias.equals(account.getAlias())) {
                return account;
            }
        }
        return null;
    }

    /**
     * Delete account by iban boolean.
     *
     * @param iban the iban
     * @return the boolean
     */
    public boolean deleteAccountByIBAN(final String iban) {
        for (final Account account : accounts) {
            if (account.getIban().equals(iban)) {
                if (account.getBalance() == 0) {
                    this.getAccounts().remove(account);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Delete card by card number card.
     *
     * @param cardNumber the card number
     * @return the card
     */
    public Card deleteCardByCardNumber(final String cardNumber) {
        for (final Account account : accounts) {
            for (final Card card : account.getCards()) {
                if (card.getCardNumber().equals(cardNumber)) {
                    account.getCards().remove(card);
                    return card;
                }
            }
        }

        return null;
    }

    /**
     * Gets card by card number.
     *
     * @param cardNumber the card number
     * @return the card by card number
     */
    public Card getCardByCardNumber(final String cardNumber) {
        for (final Account account : accounts) {
            for (final Card card : account.getCards()) {
                if (card.getCardNumber().equals(cardNumber)) {
                    return card;
                }
            }
        }
        return null;
    }

    public Account getAccountByCardNumber(final String cardNumber) {
        for (final Account account : accounts) {
            for (final Card card : account.getCards()) {
                if (card.getCardNumber().equals(cardNumber)) {
                    return account;
                }
            }
        }
        return null;
    }

    /**
     * Gets transactions history.
     *
     * @return the transactions history
     */
    public List<BaseTransaction> getTransactionsHistory() {
        final List<BaseTransaction> allTransactions = new ArrayList<>();

        for (final Account account : accounts) {
            allTransactions.addAll(account.getTransactionsHistory());
        }

        Collections.sort(allTransactions);

        return allTransactions;
    }

    public void calculateAge() {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = LocalDate.parse(getBirthDate());
        int days = currentDate.getDayOfYear() - birthDate.getDayOfYear();
        int years = currentDate.getYear() - birthDate.getYear();

        if (days >= 0) {
            years++;
        }

        age = years;
    }
}
