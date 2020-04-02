package me.frostythedev.frostengine.modules.achievements.adaptor;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import me.frostythedev.frostengine.modules.achievements.player.AchievementPlayer;
import me.frostythedev.frostengine.data.JsonAdaptor;

import java.lang.reflect.Type;

public class AchievementPlayerAdaptor implements JsonAdaptor<AchievementPlayer>{
    @Override
    public AchievementPlayer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }

    @Override
    public JsonElement serialize(AchievementPlayer player, Type type, JsonSerializationContext jsonSerializationContext) {
        return null;
    }
}
