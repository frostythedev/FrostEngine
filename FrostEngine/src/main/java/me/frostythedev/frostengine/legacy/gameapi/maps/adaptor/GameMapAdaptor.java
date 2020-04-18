package me.frostythedev.frostengine.legacy.gameapi.maps.adaptor;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import me.frostythedev.frostengine.data.JsonAdaptor;
import me.frostythedev.frostengine.legacy.gameapi.maps.GameMap;
import me.frostythedev.frostengine.legacy.gameapi.maps.NullGameMap;

import java.lang.reflect.Type;

public class GameMapAdaptor implements JsonAdaptor<GameMap> {
    @Override
    public GameMap deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new NullGameMap().deserialize(jsonElement);
    }

    @Override
    public JsonElement serialize(GameMap gameMap, Type type, JsonSerializationContext jsonSerializationContext) {
        return gameMap.serialize();
    }
}
