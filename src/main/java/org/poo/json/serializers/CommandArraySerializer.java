package org.poo.json.serializers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.poo.command.BaseCommand;

import java.lang.reflect.Type;

/**
 * The type Command array serializer.
 */
public final class CommandArraySerializer implements JsonSerializer<BaseCommand[]> {
    @Override
    public JsonElement serialize(final BaseCommand[] src, final Type typeOfSrc,
                                 final JsonSerializationContext context) {
        final JsonArray jsonArray = new JsonArray();

        for (final BaseCommand baseCommand : src) {
            if (baseCommand.getOutput() == null) {
                continue;
            }
            jsonArray.add(context.serialize(baseCommand));
        }

        return jsonArray;
    }
}
