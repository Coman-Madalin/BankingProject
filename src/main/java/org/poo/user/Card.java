package org.poo.user;

import lombok.Getter;
import lombok.Setter;

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

    /**
     * Instantiates a new Card.
     */
    public Card() {
        cardNumber = generateCardNumber();
    }

    /**
     * Instantiates a new Card.
     *
     * @param isOneTimeCard the is one time card
     */
    public Card(final boolean isOneTimeCard) {
        cardNumber = generateCardNumber();
        this.isOneTimeCard = isOneTimeCard;
    }
}
