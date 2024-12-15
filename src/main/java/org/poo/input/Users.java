package org.poo.input;

import lombok.Getter;
import org.poo.user.Account;
import org.poo.user.Card;
import org.poo.user.User;

import java.util.List;

@Getter
public class Users {
    private List<User> users;

    public void addAlias(final String aliasName, final String email, final String IBAN) {
        final Account account = getAccountByEmailAndIBAN(email, IBAN);
        account.setAlias(aliasName);
    }

    private Account checkAlias(final String name) {
        for (final User user : users) {
            final Account account = user.getAccountByAlias(name);
            if (account != null) {
                return account;
            }
        }

        return null;
    }

    public User getUserByEmail(final String email) {
        for (final User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }

        return null;
    }

    public User getUserByIBAN(final String IBAN) {
        for (final User user : users) {
            for (final Account account : user.getAccounts()) {
                if (account.getIBAN().equals(IBAN) || IBAN.equals(account.getAlias())) {
                    return user;
                }
            }
        }

        return null;
    }

    public Account getAccountByEmailAndIBAN(final String email, final String IBAN) {
        final Account aliasAccount = checkAlias(IBAN);
        if (aliasAccount != null) {
            return aliasAccount;
        }

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
        final Account accountAlias = checkAlias(IBAN);
        if (accountAlias != null) {
            return accountAlias;
        }


        final Account aliasAccount = checkAlias(IBAN);
        if (aliasAccount != null) {
            return aliasAccount;
        }

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

    public Card getCardByCardNumber(final String cardNumber) {
        for (final User user : users) {
            for (final Account account : user.getAccounts()) {
                final Card result = account.getCardByCardNumber(cardNumber);
                if (result != null)
                    return result;
            }
        }
        return null;
    }

    public Account getAccountByCardNumber(final String cardNumber) {
        for (final User user : users) {
            for (final Account account : user.getAccounts()) {
                if (account.getCardByCardNumber(cardNumber) != null)
                    return account;
            }
        }
        return null;
    }

    public User getUserByCardNumber(final String cardNumber) {
        for (final User user : users) {
            for (final Account account : user.getAccounts()) {
                if (account.getCardByCardNumber(cardNumber) != null)
                    return user;
            }
        }
        return null;
    }
}
