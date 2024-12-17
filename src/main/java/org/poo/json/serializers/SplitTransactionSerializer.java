package org.poo.json.serializers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.poo.transactions.specific.SplitTransaction;

import java.lang.reflect.Type;

/**
 * The type Split transaction serializer.
 */
public final class SplitTransactionSerializer implements JsonSerializer<SplitTransaction> {
    @Override
    public JsonElement serialize(final SplitTransaction src, final Type typeOfSrc,
                                 final JsonSerializationContext context) {
        final JsonObject jsonObject = new Gson().toJsonTree(src).getAsJsonObject();
        if (src.getError() == null) {
            jsonObject.remove("error");
        }

        return jsonObject;
    }
}
