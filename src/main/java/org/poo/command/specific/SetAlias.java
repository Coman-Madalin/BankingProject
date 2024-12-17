package org.poo.command.specific;

import org.poo.command.BaseCommand;
import org.poo.input.Input;

/**
 * The type Set alias.
 */
public final class SetAlias extends BaseCommand {
    private String email;
    private String account;
    private String alias;

    /**
     * Instantiates a new Set alias.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public SetAlias(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
        input.getUsers().addAlias(alias, email, account);
    }
}
