package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;

public class SetAlias extends BaseCommand {
    private String email;
    private String account;
    private String alias;

    public SetAlias(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
        input.getUsers().addAlias(alias, email, account);
    }
}
