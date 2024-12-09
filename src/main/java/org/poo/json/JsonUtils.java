package org.poo.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.poo.Input;
import org.poo.command.BaseCommand;
import org.poo.json.deserializers.InputDeserializer;
import org.poo.json.serializers.CommandArraySerializer;

public class JsonUtils {
    @Getter
    private static final Gson gson;

    static {
        gson = new GsonBuilder()
                .registerTypeAdapterFactory(new BaseCommandTypeAdapterFactory())
                .registerTypeAdapter(BaseCommand[].class, new CommandArraySerializer())
                .registerTypeAdapter(Input.class, new InputDeserializer())
                .create();
    }
}
