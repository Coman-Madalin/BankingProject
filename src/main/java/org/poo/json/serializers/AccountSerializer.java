package org.poo.json.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.poo.account.Account;
import org.poo.json.JsonUtils;

import java.lang.reflect.Type;

/**
 * The type Account serializer.
 */
public final class AccountSerializer implements JsonSerializer<Account> {
    @Override
    public JsonElement serialize(final Account src, final Type typeOfSrc,
                                 final JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("IBAN", src.getIban());
        jsonObject.addProperty("balance", src.getBalance());
        jsonObject.addProperty("currency", src.getCurrency());
        jsonObject.addProperty("type", src.getType());
        jsonObject.add("cards", JsonUtils.getGSON().toJsonTree(src.getCards()));

        return jsonObject;
    }
}
