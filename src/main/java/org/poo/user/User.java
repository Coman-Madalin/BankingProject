package org.poo.user;

import lombok.Getter;
import org.poo.transactions.BaseTransaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type User.
 */
@Getter
public final class User {
    private String firstName;
    private String lastName;
    private String email;
    private List<Account> accounts = new ArrayList<>();

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
}
