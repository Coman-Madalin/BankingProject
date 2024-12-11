package org.poo.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.poo.command.BaseCommand;
import org.poo.command.specific.*;
import org.poo.input.Input;
import org.poo.json.deserializers.InputDeserializer;
import org.poo.json.serializers.AccountSerializer;
import org.poo.json.serializers.CommandArraySerializer;
import org.poo.user.Account;

public class JsonUtils {
    @Getter
    private static final Gson gson;

    private static final Class<?>[] command_subclasses = {
            BaseCommand.class,
            AddAccount.class,
            AddFunds.class,
            CreateCard.class,
            PrintUsers.class,
            DeleteAccount.class,
            CreateOneTimeCard.class,
            DeleteCard.class,
            PayOnline.class,
            SendMoney.class
    };

    static {
        final GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(BaseCommand[].class, new CommandArraySerializer())
                .registerTypeAdapter(Input.class, new InputDeserializer())
                .registerTypeAdapter(Account.class, new AccountSerializer());
        //                .disableHtmlEscaping();
//                .create();

        for (final Class<?> subclass : command_subclasses) {
            gsonBuilder.registerTypeAdapter(subclass, new BaseCommandTypeAdapter());
        }

        gson = gsonBuilder.create();
    }
}
