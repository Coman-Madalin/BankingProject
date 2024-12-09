package org.poo.json;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.poo.command.BaseCommand;

public class BaseCommandTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (BaseCommand.class.isAssignableFrom(type.getRawType())) {
            return (TypeAdapter<T>) new BaseCommandTypeAdapter();
        }
        return null;  // Return null for non-BaseCommand types
    }
}
