package org.poo.command.specific;

import com.google.gson.Gson;
import org.poo.Input;
import org.poo.command.BaseCommand;

public class PrintUsers extends BaseCommand {
    public PrintUsers(String command, int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(Input input) {
        Gson gson = new Gson();

        this.setOutput(gson.toJson(input.getUsers()));
        System.out.println(this.getOutput());
    }

}
