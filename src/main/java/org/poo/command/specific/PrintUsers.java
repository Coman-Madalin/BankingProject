package org.poo.command.specific;

import com.google.gson.Gson;
import org.poo.input.Input;
import org.poo.command.BaseCommand;
import org.poo.json.JsonUtils;

public class PrintUsers extends BaseCommand {
    public PrintUsers(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
        final Gson gson = JsonUtils.getGson();

        this.setOutput(gson.toJson(input.getUsers()));
        System.out.println(this.getOutput());
    }

}
