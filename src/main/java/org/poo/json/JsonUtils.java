package org.poo.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.poo.Input;
import org.poo.command.BaseCommand;
import org.poo.command.specific.AddAccount;
import org.poo.command.specific.AddFunds;
import org.poo.command.specific.CreateCard;
import org.poo.command.specific.PrintUsers;
import org.poo.json.deserializers.InputDeserializer;
import org.poo.json.serializers.CommandArraySerializer;

public class JsonUtils {
    @Getter
    private static final Gson gson;

    private static final Class<?>[] command_subclasses = {
            BaseCommand.class,
            AddAccount.class,
            AddFunds.class,
            CreateCard.class,
            PrintUsers.class
    };

    static {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(BaseCommand[].class, new CommandArraySerializer())
                .registerTypeAdapter(Input.class, new InputDeserializer());
//                .disableHtmlEscaping();
//                .create();

        for (Class<?> subclass : command_subclasses) {
            gsonBuilder.registerTypeAdapter(subclass, new BaseCommandTypeAdapter());
        }

        gson = gsonBuilder.create();
    }
}
