package org.poo.user;

import lombok.Getter;
import lombok.Setter;

import static org.poo.utils.Utils.generateCardNumber;

@Setter
@Getter
public class Card {
    private String cardNumber;
    private String status = "active";
    private boolean isOneTimeCard = false;

    public Card() {
        cardNumber = generateCardNumber();
    }

    public Card(final boolean isOneTimeCard) {
        cardNumber = generateCardNumber();
        this.isOneTimeCard = isOneTimeCard;
    }
}
