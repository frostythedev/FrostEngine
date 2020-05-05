package me.frostythedev.frostengine.modules.gameapi.kits.adaptor;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import me.frostythedev.frostengine.modules.gameapi.kits.GameKit;
import me.frostythedev.frostengine.modules.gameapi.kits.NullGameKit;
import me.frostythedev.frostengine.data.JsonAdaptor;

import java.lang.reflect.Type;

public class GameKitAdaptor implements JsonAdaptor<GameKit>{

    @Override
    public GameKit deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new NullGameKit().deserialize(jsonElement);
    }

    @Override
    public JsonElement serialize(GameKit gameKit, Type type, JsonSerializationContext jsonSerializationContext) {
        return gameKit.serialize();
    }
}
