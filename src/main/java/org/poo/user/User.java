package org.poo.user;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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

    public boolean deleteAccountByIBAN(final String IBAN) {
        for (final Account account : accounts) {
            if (account.getIBAN().equals(IBAN)) {
                this.getAccounts().remove(account);
                return true;
            }
        }

        return false;
    }

    public boolean deleteCardByCardNumber(final String cardNumber) {
        for (final Account account : accounts) {
            for (final Card card : account.getCards()) {
                if (card.getCardNumber().equals(cardNumber)) {
                    account.getCards().remove(card);
                    return true;
                }
            }
        }

        return false;
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
}
