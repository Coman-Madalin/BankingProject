package org.poo;

import com.google.gson.Gson;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.poo.command.BaseCommand;
import org.poo.commerciant.Commerciant;
import org.poo.exchange.Exchange;
import org.poo.json.JsonUtils;
import org.poo.user.User;

import java.io.FileWriter;
import java.io.IOException;


@Data
@NoArgsConstructor
public final class Input {
//    private static Input instance;

    private User[] users;
    private Exchange[] exchangeRates;
    private BaseCommand[] commands;
    private Commerciant[] commerciants;

//    public Input(){
//        instance = this;
//    }

    public void executeAllCommands() {
//        Gson gson = new Gson();
        for (BaseCommand command : commands) {
            command.execute(this);
        }
    }

    public void gamesToJson(final String filePath1) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath1)) {
            Gson gson = JsonUtils.getGson();
            gson.toJson(commands, fileWriter);
        }
    }
}
