package org.poo.command;

public class BaseCommand {
    String command;
    int timestamp;

    public BaseCommand(String command, int timestamp) {
        this.command = command;
        this.timestamp = timestamp;
    }
}
