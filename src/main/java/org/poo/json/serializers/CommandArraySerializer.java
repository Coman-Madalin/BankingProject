package org.poo.json.serializers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.poo.command.BaseCommand;

import java.lang.reflect.Type;

public class CommandArraySerializer implements JsonSerializer<BaseCommand[]> {
    @Override
    public JsonElement serialize(BaseCommand[] src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray jsonArray = new JsonArray();

        for (BaseCommand baseCommand : src) {
            if (baseCommand.getOutput() == null) {
                continue;
            }
            jsonArray.add(context.serialize(baseCommand));
        }

        return jsonArray;
    }
}
