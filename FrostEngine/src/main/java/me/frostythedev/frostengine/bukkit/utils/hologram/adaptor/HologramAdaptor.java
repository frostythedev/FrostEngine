package me.frostythedev.frostengine.bukkit.utils.hologram.adaptor;

import com.google.gson.*;
import me.frostythedev.frostengine.bukkit.utils.hologram.Hologram;
import me.frostythedev.frostengine.bukkit.utils.StringUtil;
import me.frostythedev.frostengine.data.JsonAdaptor;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import org.bukkit.Location;

import java.lang.reflect.Type;
import java.util.List;

public class HologramAdaptor implements JsonAdaptor<Hologram> {
    @Override
    public Hologram deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String name = jsonObject.get("name").getAsString();
        List<String> lines = StringUtil.stringToList(jsonObject.get("lines").getAsString());
        Location location = FEPlugin.getGson().fromJson(jsonObject.get("location").getAsString(), Location.class);

        return new Hologram(name, lines, location);
    }

    @Override
    public JsonElement serialize(Hologram hologram, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        String name = hologram.getName();
        List<String> lines = hologram.getLines();
        Location location = hologram.getLocation();

        jsonObject.addProperty("name", name);
        jsonObject.addProperty("lines", StringUtil.listToString(lines));
        jsonObject.addProperty("location", FEPlugin.getGson().toJson(location));
        return jsonObject;
    }
}
