package org.poo.command.specific;

import com.google.gson.Gson;
import org.poo.command.BaseCommand;
import org.poo.input.Input;
import org.poo.json.JsonUtils;

/**
 * The type Print users.
 */
public final class PrintUsers extends BaseCommand {
    /**
     * Instantiates a new Print users.
     *
     * @param command   the command
     * @param timestamp the timestamp
     */
    public PrintUsers(final String command, final int timestamp) {
        super(command, timestamp);
    }

    @Override
    public void execute(final Input input) {
        final Gson gson = JsonUtils.getGSON();

        this.setOutput(gson.toJson(input.getUsers().getUsers()));
    }

}
