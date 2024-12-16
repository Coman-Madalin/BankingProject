package org.poo.user;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.poo.transactions.BaseTransaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@Setter
@Getter
public final class User {
    private String firstName;
    private String lastName;
    private String email;
    private List<Account> accounts = new ArrayList<>();

    public Account getAccountByAlias(final String alias) {
        for (final Account account : accounts) {
            if (alias.equals(account.getAlias())) {
                return account;
            }
        }
        return null;
    }

    public boolean deleteAccountByIBAN(final String IBAN) {
        for (final Account account : accounts) {
            if (account.getIBAN().equals(IBAN)) {
                if (account.getBalance() == 0) {
                    this.getAccounts().remove(account);
                    return true;
                }
            }
        }

        return false;
    }

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

    public List<BaseTransaction> getTransactionsHistory() {
        final List<BaseTransaction> allTransactions = new ArrayList<>();

        for (final Account account : accounts) {
            allTransactions.addAll(account.getTransactionsHistory());
        }

        Collections.sort(allTransactions);

        return allTransactions;
    }
}
