package org.poo.input;

import lombok.Getter;
import org.poo.user.Account;
import org.poo.user.Card;
import org.poo.user.User;

import java.util.List;

@Getter
public class Users {
    private List<User> users;

    public User getUserByEmail(final String email) {
        for (final User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }

        return null;
    }

    public Account getAccountByEmailAndIBAN(final String email, final String IBAN) {
        for (final User user : users) {
            if (!user.getEmail().equals(email)) {
                continue;
            }

            for (final Account account : user.getAccounts()) {
                if (account.getIBAN().equals(IBAN)) {
                    return account;
                }
            }
        }

        return null;
    }

    public Account getAccountByIBAN(final String IBAN) {
        for (final User user : users) {
            for (final Account account : user.getAccounts()) {
                if (account.getIBAN().equals(IBAN)) {
                    return account;
                }
            }
        }

        return null;
    }

    public Account getAccountByEmailAndCardNumber(final String email, final String cardNumber) {
        for (final User user : users) {
            if (!user.getEmail().equals(email)) {
                continue;
            }

            for (final Account account : user.getAccounts()) {
                for (final Card card : account.getCards()) {
                    if (card.getCardNumber().equals(cardNumber))
                        return account;
                }
            }
        }

        return null;
    }

    public Card getCardByEmailAndCardNumber(final String email, final String cardNumber) {
        for (final User user : users) {
            if (!user.getEmail().equals(email)) {
                continue;
            }

            for (final Account account : user.getAccounts()) {
                for (final Card card : account.getCards()) {
                    if (card.getCardNumber().equals(cardNumber)) {
                        return card;
                    }
                }
            }
        }

        return null;
    }

}
