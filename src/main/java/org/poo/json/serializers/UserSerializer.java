package org.poo.json.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.poo.json.JsonUtils;
import org.poo.user.User;

import java.lang.reflect.Type;

public class UserSerializer implements JsonSerializer<User> {
    @Override
    public JsonElement serialize(final User src, final Type typeOfSrc, final JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", src.getEmail());
        jsonObject.addProperty("firstName", src.getFirstName());
        jsonObject.addProperty("lastName", src.getLastName());
        jsonObject.add("accounts", JsonUtils.getGson().toJsonTree(src.getAccounts()));

        return jsonObject;
    }
}
