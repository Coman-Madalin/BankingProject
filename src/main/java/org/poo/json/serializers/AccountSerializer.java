package org.poo.json.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.poo.json.JsonUtils;
import org.poo.user.Account;

import java.lang.reflect.Type;

public class AccountSerializer implements JsonSerializer<Account> {
    @Override
    public JsonElement serialize(Account src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("IBAN", src.getIBAN());
        jsonObject.addProperty("balance", src.getBalance());
        jsonObject.addProperty("currency", src.getCurrency());
        jsonObject.addProperty("type", src.getType());
        jsonObject.add("cards", JsonUtils.getGson().toJsonTree(src.getCards()));

        return jsonObject;
    }
}
