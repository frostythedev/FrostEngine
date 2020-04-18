package me.frostythedev.frostengine.data;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public interface JsonAdaptor<T> extends JsonDeserializer<T>, JsonSerializer<T> {
}
