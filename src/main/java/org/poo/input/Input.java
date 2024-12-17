package org.poo.input;

import com.google.gson.Gson;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.poo.command.BaseCommand;
import org.poo.commerciant.Commerciant;
import org.poo.json.JsonUtils;

import java.io.FileWriter;
import java.io.IOException;


/**
 * The type Input.
 */
@Data
@NoArgsConstructor
public final class Input {
    private Users users;
    private Exchanges exchanges;
    private BaseCommand[] commands;
    private Commerciant[] commerciants;

    /**
     * Is alias boolean.
     *
     * @param account the account
     * @return the boolean
     */
    public static boolean isAlias(final String account) {
        return !account.startsWith("RO");
    }

    /**
     * Execute all commands.
     */
    public void executeAllCommands() {
        exchanges.makeCommonCurrencyExchange();
        for (final BaseCommand command : commands) {
            command.execute(this);
        }
    }

    /**
     * Games to json.
     *
     * @param filePath1 the file path 1
     * @throws IOException the io exception
     */
    public void gamesToJson(final String filePath1) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath1)) {
            final Gson gson = JsonUtils.getGSON();
            gson.toJson(commands, fileWriter);
        }
    }
}
