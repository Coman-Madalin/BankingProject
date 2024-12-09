package org.poo.command.specific;

import org.poo.Input;
import org.poo.command.BaseCommand;

public class AddFunds extends BaseCommand {
    String account;
    int amount;

    public AddFunds(String command, int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(Input input) {

    }
}
