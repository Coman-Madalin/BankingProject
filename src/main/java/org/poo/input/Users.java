package org.poo.input;

import lombok.Getter;
import org.poo.account.BaseAccount;
import org.poo.commerciant.Commerciant;
import org.poo.user.Card;
import org.poo.user.Data;
import org.poo.user.User;

import java.util.List;
import java.util.Map;

/**
 * The type Users.
 */
@Getter
public class Users {
    private List<User> users;

    /**
     * Add alias.
     *
     * @param aliasName the alias name
     * @param email     the email
     * @param iban      the iban
     */
    public void addAlias(final String aliasName, final String email, final String iban) {
        final BaseAccount account = getAccountByEmailAndIBAN(email, iban);
        account.setAlias(aliasName);
    }

    private BaseAccount checkAlias(final String name) {
        for (final User user : users) {
            final BaseAccount account = user.getAccountByAlias(name);
            if (account != null) {
                return account;
            }
        }

        return null;
    }

    /**
     * Gets user by email.
     *
     * @param email the email
     * @return the user by email
     */
    public User getUserByEmail(final String email) {
        if (email == null) {
            return null;
        }

        for (final User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }

        return null;
    }

    /**
     * Gets user by iban.
     *
     * @param iban the iban
     * @return the user by iban
     */
    public User getUserByIBAN(final String iban) {
        for (final User user : users) {
            for (final BaseAccount account : user.getAccounts()) {
                if (account.getIban().equals(iban) || iban.equals(account.getAlias())) {
                    return user;
                }
            }
        }

        return null;
    }

    /**
     * Gets account by email and iban.
     *
     * @param email the email
     * @param iban  the iban
     * @return the account by email and iban
     */
    public BaseAccount getAccountByEmailAndIBAN(final String email, final String iban) {
        final BaseAccount aliasAccount = checkAlias(iban);
        if (aliasAccount != null) {
            return aliasAccount;
        }

        for (final User user : users) {
            if (!user.getEmail().equals(email)) {
                continue;
            }

            for (final BaseAccount account : user.getAccounts()) {
                if (account.getIban().equals(iban)) {
                    return account;
                }
            }
        }

        return null;
    }

    /**
     * Gets account by iban.
     *
     * @param iban the iban
     * @return the account by iban
     */
    public BaseAccount getAccountByIBAN(final String iban) {
        final BaseAccount accountAlias = checkAlias(iban);
        if (accountAlias != null) {
            return accountAlias;
        }


        final BaseAccount aliasAccount = checkAlias(iban);
        if (aliasAccount != null) {
            return aliasAccount;
        }

        for (final User user : users) {
            for (final BaseAccount account : user.getAccounts()) {
                if (account.getIban().equals(iban)) {
                    return account;
                }
            }
        }

        return null;
    }

    /**
     * Gets account by email and card number.
     *
     * @param email      the email
     * @param cardNumber the card number
     * @return the account by email and card number
     */
    public BaseAccount getAccountByEmailAndCardNumber(final String email, final String cardNumber) {
        for (final User user : users) {
            if (!user.getEmail().equals(email)) {
                continue;
            }

            for (final BaseAccount account : user.getAccounts()) {
                for (final Card card : account.getCards()) {
                    if (card.getCardNumber().equals(cardNumber)) {
                        return account;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Gets card by email and card number.
     *
     * @param email      the email
     * @param cardNumber the card number
     * @return the card by email and card number
     */
    public Card getCardByEmailAndCardNumber(final String email, final String cardNumber) {
        for (final User user : users) {
            if (!user.getEmail().equals(email)) {
                continue;
            }

            for (final BaseAccount account : user.getAccounts()) {
                for (final Card card : account.getCards()) {
                    if (card.getCardNumber().equals(cardNumber)) {
                        return card;
                    }
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
        for (final User user : users) {
            for (final BaseAccount account : user.getAccounts()) {
                final Card result = account.getCardByCardNumber(cardNumber);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * Gets account by card number.
     *
     * @param cardNumber the card number
     * @return the account by card number
     */
    public BaseAccount getAccountByCardNumber(final String cardNumber) {
        for (final User user : users) {
            for (final BaseAccount account : user.getAccounts()) {
                if (account.getCardByCardNumber(cardNumber) != null) {
                    return account;
                }
            }
        }
        return null;
    }

    /**
     * Gets user by card number.
     *
     * @param cardNumber the card number
     * @return the user by card number
     */
    public User getUserByCardNumber(final String cardNumber) {
        for (final User user : users) {
            for (final BaseAccount account : user.getAccounts()) {
                if (account.getCardByCardNumber(cardNumber) != null) {
                    return user;
                }
            }
        }
        return null;
    }

    /**
     * Print all.
     *
     * @param time the time
     */
    public void printAll(final int time) {
        System.out.println("[");
        System.out.println("!!!!TIME=" + time + "!!!!");
        for (final User user : users) {
            System.out.println("    {");
            System.out.println("    email: " + user.getEmail());
            System.out.println("    plan: " + user.getServicePlan());
            System.out.println("    Accounts: [");
            for (final BaseAccount account : user.getAccounts()) {
                System.out.println("        Iban: " + account.getIban());
                System.out.println("        Balance: " + account.getBalance());
                System.out.println("        Currency: " + account.getCurrency());
                System.out.println("        Commerciants data: [");
                for (final Map.Entry<Commerciant, Data> commerciantDataEntry
                        : account.getCOMMERCIANT_TO_DATA().entrySet()) {
                    System.out.println("            {");
                    System.out.println("            Commerciant name = "
                            + commerciantDataEntry.getKey().getCommerciant());
                    System.out.println("            Spend = "
                            + commerciantDataEntry.getValue().getTotalSpend());
                    System.out.println("            Transactions = "
                            + commerciantDataEntry.getValue().getNrTransactions());

                    System.out.println("            }");
                }
                System.out.println("        ],");
            }
            System.out.println("    ],");
            System.out.println("    },");
        }
        System.out.println("]\n\n");
    }

    /**
     * Print specific iban.
     *
     * @param time the time
     * @param iban the iban
     */
    public void printSpecificIBAN(final int time, final String iban) {
        final BaseAccount account = Input.getInstance().getUsers().getAccountByIBAN(iban);
        if (account == null) {
            return;
        }

        System.out.println("\n\n");

        System.out.println("!!!!TIME=" + time + "!!!!");
        System.out.println("Iban: " + account.getIban());
        System.out.println("Balance: " + account.getBalance() + " " + account.getCurrency());
        System.out.println("Cards: [");
        for (Card card : account.getCards()) {
            System.out.println("    {");
            System.out.println("    cardNumber: " + card.getCardNumber());
            System.out.println("    isOneTime: " + card.isOneTimeCard());
            System.out.println("    }");
        }
        System.out.println("]");
        System.out.println("\n\n");

    }
}
