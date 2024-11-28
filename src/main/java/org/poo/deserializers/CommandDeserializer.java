package org.poo.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.poo.command.BaseCommand;
import org.poo.command.specific.AddAccount;
import org.poo.command.specific.AddFunds;
import org.poo.command.specific.CreateCard;
import org.poo.command.specific.PrintUsers;

import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.Map;

public class CommandDeserializer implements JsonDeserializer<BaseCommand> {
    private static final Map<String, Class<?>> NAME_TO_COMMAND_CLASS = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("printUsers", PrintUsers.class),
            new AbstractMap.SimpleEntry<>("addAccount", AddAccount.class),
            new AbstractMap.SimpleEntry<>("createCard", CreateCard.class),
            new AbstractMap.SimpleEntry<>("addFunds", AddFunds.class)
    );

    @Override
    public BaseCommand deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String name = json.getAsJsonObject().getAsJsonPrimitive("command").getAsString();
        Class<?> clazz = NAME_TO_COMMAND_CLASS.get(name);
        return context.deserialize(json, clazz);
    }
}
