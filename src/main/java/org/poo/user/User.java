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

    public boolean increaseNumberOfOver300Payments() {
        if (servicePlan != ServicePlans.SILVER) {
            System.out.printf("%s doesn't have silver plan\n", getEmail());
            return false;
        }

        numberOfOver300Payments++;


        if (numberOfOver300Payments == 5) {
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

    public BaseAccount getClassicAccountInCurrency(String currency) {
        for (BaseAccount account : accounts) {
            if (account.getType().equals("classic") && account.getCurrency().equalsIgnoreCase(currency)) {
                return account;
            }
        }
        return null;
    }

    public SplitPaymentParticipant getFirstSplitPayment(String type) {
        for (SplitPaymentParticipant splitPaymentParticipant : splitPaymentParticipantList) {
            if (splitPaymentParticipant.getMediator().getPaymentCommand().getSplitPaymentType()
                    .equalsIgnoreCase(type)) {
                return splitPaymentParticipant;
            }
        }
        return null;
    }

    public void removeSplitPayment(SplitPaymentParticipant splitPayment) {
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
