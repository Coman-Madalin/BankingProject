package org.poo.command.specific;

import org.poo.Input;
import org.poo.command.BaseCommand;

public class AddAccount extends BaseCommand {
    String email;
    String currency;
    String accountType;

    public AddAccount(String command, int timestamp, String email, String currency,
                      String accountType) {
        super(command, timestamp);
        this.email = email;
        this.currency = currency;
        this.accountType = accountType;
    }

    @Override
    public void execute(Input input) {

    }
}
