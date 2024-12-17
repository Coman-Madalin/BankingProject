package org.poo.command;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Base command.
 */
@Getter
@Setter
public abstract class BaseCommand {
    private String command;
    private int timestamp;
    private String output;

    /**
     * Instantiates a new Base command.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public BaseCommand(final String command, final int timestamp) {
        this.command = command;
        this.timestamp = timestamp;
    }

    /**
     * Execute.
     */
    public abstract void execute();
}
