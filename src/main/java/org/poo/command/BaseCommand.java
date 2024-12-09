package org.poo.command;

import lombok.Getter;
import lombok.Setter;
import org.poo.Input;

@Getter
@Setter
public abstract class BaseCommand {
    private String command;
    private int timestamp;
    private String output;

    public BaseCommand(String command, int timestamp) {
        this.command = command;
        this.timestamp = timestamp;
    }

    public abstract void execute(Input input);
}
