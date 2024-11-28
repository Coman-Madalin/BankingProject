package org.poo.command.specific;

import org.poo.command.BaseCommand;

public class CreateCard extends BaseCommand {
    String account;
    String email;

    public CreateCard(String command, int timestamp) {
        super(command, timestamp);
    }
}
