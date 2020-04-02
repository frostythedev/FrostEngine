package me.frostythedev.frostengine.modules.ranks.adaptor;

import com.google.gson.*;
import me.frostythedev.frostengine.bukkit.utilities.StringUtil;
import me.frostythedev.frostengine.data.JsonAdaptor;
import me.frostythedev.frostengine.modules.ranks.objects.Rank;

import java.lang.reflect.Type;
import java.util.List;

public class RankAdaptor implements JsonAdaptor<Rank> {
    @Override
    public Rank deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int id = jsonObject.get("id").getAsInt();
        String name = jsonObject.get("name").getAsString();
        String server = jsonObject.get("server").getAsString();
        String prefix = jsonObject.get("prefix").getAsString();
        String suffix = jsonObject.get("suffix").getAsString();
        int priority = jsonObject.get("priority").getAsInt();
        List<String> permissions = StringUtil.stringToList(jsonObject.get("permissions").getAsString());
        List<String> inheritance = StringUtil.stringToList(jsonObject.get("inheritance").getAsString());

        return new Rank(id, name, server, prefix, suffix, priority, permissions, inheritance);
    }

    @Override
    public JsonElement serialize(Rank rank, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        int id = rank.getId();
        String name = rank.getName();
        String server = rank.getServer();
        String prefix = rank.getPrefix();
        String suffix = rank.getSuffix();
        int priority = rank.getPriority();
        List<String> permissions = rank.getPermissions();
        List<String> inheritance = rank.getInheritance();

        jsonObject.addProperty("id", id);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("server", server);
        jsonObject.addProperty("prefix", prefix);
        jsonObject.addProperty("suffix", suffix);
        jsonObject.addProperty("priority", priority);
        jsonObject.addProperty("permissions", StringUtil.listToString(permissions));
        jsonObject.addProperty("inheritance", StringUtil.listToString(inheritance));
        return jsonObject;
    }
}
