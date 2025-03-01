package org.poo.user;

import lombok.Getter;
import lombok.Setter;
import org.poo.account.BaseAccount;

import static org.poo.utils.Utils.generateCardNumber;

/**
 * The type Card.
 */
@Setter
@Getter
public class Card {
    private String cardNumber;
    private String status = "active";
    private boolean isOneTimeCard = false;

    private BaseAccount account;

    /**
     * Instantiates a new Card.
     *
     * @param account the account
     */
    public Card(final BaseAccount account) {
        cardNumber = generateCardNumber();
        this.account = account;
    }

    /**
     * Instantiates a new Card.
     *
     * @param isOneTimeCard the is one time card
     * @param account       the account
     */
    public Card(final boolean isOneTimeCard, final BaseAccount account) {
        cardNumber = generateCardNumber();
        this.isOneTimeCard = isOneTimeCard;
        this.account = account;
    }
}
