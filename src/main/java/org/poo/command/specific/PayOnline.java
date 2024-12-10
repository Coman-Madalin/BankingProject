package org.poo.command.specific;

import org.poo.Input;
import org.poo.command.BaseCommand;
import org.poo.user.Account;
import org.poo.user.Card;
import org.poo.user.User;

public class PayOnline extends BaseCommand {
    private String cardNumber;
    private int amount;
    private String currency;
    private String description;
    private String commerciant;
    private String email;

    public PayOnline(String command, int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(Input input) {
        for (User user : input.getUsers()) {
            if (!user.getEmail().equals(this.email)) {
                continue;
            }

            for (Account userAccount : user.getAccounts()) {
                for (Card card : userAccount.getCards()) {
                    if(card.getCardNumber().equals(this.cardNumber)){
                        userAccount.increaseBalance(-amount);
                        return;
                    }
                }
            }
        }
    }
}
