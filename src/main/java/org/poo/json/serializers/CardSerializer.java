package org.poo.json.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.poo.user.Card;

import java.lang.reflect.Type;

/**
 * The type Card serializer.
 */
public final class CardSerializer implements JsonSerializer<Card> {
    @Override
    public JsonElement serialize(final Card src, final Type typeOfSrc,
                                 final JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("cardNumber", src.getCardNumber());
        jsonObject.addProperty("status", src.getStatus());

        return jsonObject;

    }
}
