package org.poo.command;

import lombok.Getter;
import lombok.Setter;
import org.poo.input.Input;

@Getter
@Setter
public abstract class BaseCommand {
    private String command;
    private int timestamp;
    private String output;

    public BaseCommand(final String command, final int timestamp) {
        this.command = command;
        this.timestamp = timestamp;
    }

    public abstract void execute(Input input);
}
