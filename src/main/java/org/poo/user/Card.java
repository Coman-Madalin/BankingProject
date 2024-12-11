package org.poo.user;

import lombok.Getter;
import lombok.Setter;

import static org.poo.utils.Utils.generateCardNumber;

//@NoArgsConstructor
@Setter
@Getter
public class Card {
    private String cardNumber;
    private String status = "active";

    public Card() {
        cardNumber = generateCardNumber();
    }
}
