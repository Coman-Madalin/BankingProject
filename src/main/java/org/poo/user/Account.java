package org.poo.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Account {
    private String IBAN;
    private int balance;
    private String currency;
    private String type;
    private Card cards;
}
