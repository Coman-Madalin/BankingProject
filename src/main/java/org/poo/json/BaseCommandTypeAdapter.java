package org.poo.json;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.poo.command.BaseCommand;
import org.poo.command.specific.*;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;

public class BaseCommandTypeAdapter extends TypeAdapter<BaseCommand> {

    private static final Map<String, Class<?>> NAME_TO_COMMAND_CLASS = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("printUsers", PrintUsers.class),
            new AbstractMap.SimpleEntry<>("addAccount", AddAccount.class),
            new AbstractMap.SimpleEntry<>("createCard", CreateCard.class),
            new AbstractMap.SimpleEntry<>("addFunds", AddFunds.class),
            new AbstractMap.SimpleEntry<>("deleteAccount", DeleteAccount.class)
    );

    @Override
    public void write(JsonWriter out, BaseCommand value) throws IOException {
        out.beginObject();
        out.name("command");
        out.value(value.getCommand());

        out.name("timestamp");
        out.value(value.getTimestamp());

        JsonElement outputJsonElement = JsonParser.parseString(value.getOutput());

        out.name("output");
        Gson gson = JsonUtils.getGson();
        gson.toJson(outputJsonElement, out);

        out.endObject();
    }

    @Override
    public BaseCommand read(JsonReader in) {
        JsonObject jsonObject = JsonParser.parseReader(in).getAsJsonObject();
        String commandName = jsonObject.getAsJsonPrimitive("command").getAsString();
        Class<?> clazz = NAME_TO_COMMAND_CLASS.get(commandName);

        if (clazz == null) {
            throw new JsonParseException("Unknown command type: " + commandName);
        }

        return (BaseCommand) new Gson().fromJson(jsonObject, clazz);
    }
}
