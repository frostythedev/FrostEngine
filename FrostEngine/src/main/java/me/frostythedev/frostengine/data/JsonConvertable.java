package me.frostythedev.frostengine.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public interface JsonConvertable<T> {
    JsonObject serialize();
    T deserialize(JsonElement element);
}
