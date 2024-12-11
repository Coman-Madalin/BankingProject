package org.poo.input;

import com.google.gson.Gson;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.poo.command.BaseCommand;
import org.poo.commerciant.Commerciant;
import org.poo.json.JsonUtils;

import java.io.FileWriter;
import java.io.IOException;


@Data
@NoArgsConstructor
public final class Input {
    private Users users;
    private Exchanges exchanges;
    private BaseCommand[] commands;
    private Commerciant[] commerciants;

    public void executeAllCommands() {
        exchanges.makeCommonCurrencyExchange();
        for (final BaseCommand command : commands) {
            command.execute(this);
        }
    }

    public void gamesToJson(final String filePath1) throws IOException {
        try (final FileWriter fileWriter = new FileWriter(filePath1)) {
            final Gson gson = JsonUtils.getGson();
            gson.toJson(commands, fileWriter);
        }
    }
}
