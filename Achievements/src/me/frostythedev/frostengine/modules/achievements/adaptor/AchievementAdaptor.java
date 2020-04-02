package me.frostythedev.frostengine.modules.achievements.adaptor;

import com.google.gson.*;
import me.frostythedev.frostengine.bukkit.utils.StringUtil;
import me.frostythedev.frostengine.data.JsonAdaptor;
import me.frostythedev.frostengine.modules.achievements.core.Achievement;

import java.lang.reflect.Type;
import java.util.List;

public class AchievementAdaptor implements JsonAdaptor<Achievement>{

    @Override
    public Achievement deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int id = jsonObject.get("id").getAsInt();
        String name = jsonObject.get("name").getAsString();
        boolean enabled = jsonObject.get("enabled").getAsBoolean();
        String achievementType = jsonObject.get("achievementType").getAsString();
        List<String> description = StringUtil.stringToList(jsonObject.get("description").getAsString());

        return new Achievement(id, name, enabled, achievementType, description);
    }

    @Override
    public JsonElement serialize(Achievement achievement, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        int id = achievement.getId();
        String name = achievement.getName();
        boolean enabled = achievement.isEnabled();
        List<String> description = achievement.getDescription();
        String achievementType = achievement.getAchievementType();

        jsonObject.addProperty("id", id);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("enabled", enabled);
        jsonObject.addProperty("achievementType", achievementType);
        jsonObject.addProperty("description", StringUtil.listToString(description));
        return jsonObject;
    }
}
