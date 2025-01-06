package org.poo.json.deserializers;

import com.google.gson.*;
import org.poo.command.BaseCommand;
import org.poo.input.Exchanges;
import org.poo.input.Input;
import org.poo.input.Users;

import java.lang.reflect.Type;

/**
 * The type Input deserializer.
 */
public final class InputDeserializer implements JsonDeserializer<Input> {
    @Override
    public Input deserialize(final JsonElement json, final Type typeOfT,
                             final JsonDeserializationContext context) throws JsonParseException {
        final Input toReturn = new Input();

        // TODO: Make all of them lists
        final JsonArray users = json.getAsJsonObject().getAsJsonArray("users");
        JsonObject elem = new JsonObject();
        elem.add("users", users);
        toReturn.setUsers(context.deserialize(elem, Users.class));

        final JsonArray exchangeRates = json.getAsJsonObject().getAsJsonArray("exchangeRates");
        elem = new JsonObject();
        elem.add("exchangeRates", exchangeRates);
        toReturn.setExchanges(context.deserialize(elem, Exchanges.class));

        final JsonArray commands = json.getAsJsonObject().getAsJsonArray("commands");
        toReturn.setCommands(context.deserialize(commands, BaseCommand[].class));

        return toReturn;
    }
}
