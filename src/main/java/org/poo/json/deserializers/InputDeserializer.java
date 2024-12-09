package org.poo.json.deserializers;

import com.google.gson.*;
import org.poo.Input;
import org.poo.command.BaseCommand;
import org.poo.exchange.Exchange;
import org.poo.user.User;

import java.lang.reflect.Type;

public class InputDeserializer implements JsonDeserializer<Input> {
    @Override
    public Input deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Input toReturn = new Input();

        JsonArray users = json.getAsJsonObject().getAsJsonArray("users");
        toReturn.setUsers(context.deserialize(users, User[].class));

        JsonArray exchangeRates = json.getAsJsonObject().getAsJsonArray("exchangeRates");
        toReturn.setExchangeRates(context.deserialize(exchangeRates, Exchange[].class));

        JsonArray commands = json.getAsJsonObject().getAsJsonArray("commands");
        toReturn.setCommands(context.deserialize(commands, BaseCommand[].class));

        return toReturn;
    }
}
