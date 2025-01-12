package org.poo.user;

import lombok.Getter;
import lombok.Setter;
import org.poo.account.BaseAccount;
import org.poo.command.specific.splitpayment.SplitPaymentParticipant;
import org.poo.transactions.BaseTransaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.poo.input.Input.printLog;

/**
 * The type User.
 */
@Getter
public final class User {
    private static final int NR_OF_TRANSACTIONS_FOR_AUTOMATIC_UPGRADE = 5;
    private final List<BaseAccount> accounts = new ArrayList<>();
    private final List<SplitPaymentParticipant> splitPaymentParticipantList = new ArrayList<>();
    private String firstName;
    private String lastName;
    private String email;
    private String occupation;
    private int age;
    @Setter
    private ServicePlans servicePlan = ServicePlans.STANDARD;
    private int numberOfOver300Payments = 0;
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
    public BaseAccount getAccountByAlias(final String alias) {
        for (final BaseAccount account : accounts) {
            if (alias.equals(account.getAlias())) {
                return account;
            }
        }
        return null;
    }

    /**
     * Increase number of over 300 payments boolean.
     *
     * @return the boolean
     */
    //TODO: rewrite this so we don't need the initial if before this method is called
    public boolean increaseNumberOfOver300Payments() {
        if (servicePlan != ServicePlans.SILVER) {
            System.out.printf("%s doesn't have silver plan\n", getEmail());
            return false;
        }

        numberOfOver300Payments++;

        if (numberOfOver300Payments == NR_OF_TRANSACTIONS_FOR_AUTOMATIC_UPGRADE) {
            servicePlan = ServicePlans.GOLD;
            printLog("UpgradePlan:AUTOMATIC", -1, 0, 0, getEmail());
            return true;
        }
        return false;
    }

    /**
     * Delete account by iban boolean.
     *
     * @param iban the iban
     * @return the boolean
     */
    public boolean deleteAccountByIBAN(final String iban) {
        for (final BaseAccount account : accounts) {
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
        for (final BaseAccount account : accounts) {
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
        for (final BaseAccount account : accounts) {
            for (final Card card : account.getCards()) {
                if (card.getCardNumber().equals(cardNumber)) {
                    return card;
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
        for (final BaseAccount account : accounts) {
            for (final Card card : account.getCards()) {
                if (card.getCardNumber().equals(cardNumber)) {
                    return account;
                }
            }
        }
        return null;
    }

    /**
     * Gets classic account in currency.
     *
     * @param currency the currency
     * @return the classic account in currency
     */
    public BaseAccount getClassicAccountInCurrency(final String currency) {
        for (final BaseAccount account : accounts) {
            if (account.getType().equals("classic") && account.getCurrency().equals(currency)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Gets first split payment.
     *
     * @param type the type
     * @return the first split payment
     */
    public SplitPaymentParticipant getFirstSplitPayment(final String type) {
        for (final SplitPaymentParticipant splitPaymentParticipant : splitPaymentParticipantList) {
            if (splitPaymentParticipant.getMediator().getPaymentCommand().getSplitPaymentType()
                    .equalsIgnoreCase(type)) {
                return splitPaymentParticipant;
            }
        }
        return null;
    }

    /**
     * Remove split payment.
     *
     * @param splitPayment the split payment
     */
    public void removeSplitPayment(final SplitPaymentParticipant splitPayment) {
        splitPaymentParticipantList.remove(splitPayment);
    }

    /**
     * Gets transactions history.
     *
     * @return the transactions history
     */
    public List<BaseTransaction> getTransactionsHistory() {
        final List<BaseTransaction> allTransactions = new ArrayList<>();

        for (final BaseAccount account : accounts) {
            allTransactions.addAll(account.getTransactionsHistory());
        }

        Collections.sort(allTransactions);

        return allTransactions;
    }

    /**
     * Calculate age.
     */
    public void calculateAge() {
        final LocalDate currentLocalDate = LocalDate.now();
        final LocalDate birthLocalDate = LocalDate.parse(getBirthDate());
        final int days = currentLocalDate.getDayOfYear() - birthLocalDate.getDayOfYear();
        int years = currentLocalDate.getYear() - birthLocalDate.getYear();

        if (days >= 0) {
            years++;
        }

        age = years;
    }
}
