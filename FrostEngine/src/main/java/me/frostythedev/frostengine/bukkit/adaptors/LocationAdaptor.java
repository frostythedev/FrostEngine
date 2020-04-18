package me.frostythedev.frostengine.bukkit.adaptors;

import com.google.gson.*;
import me.frostythedev.frostengine.data.JsonAdaptor;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.lang.reflect.Type;

public class LocationAdaptor implements JsonAdaptor<Location> {
    @Override
    public JsonElement serialize(Location location, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("world_name", location.getWorld().getName());
        jsonObject.addProperty("x", location.getBlockX());
        jsonObject.addProperty("y", location.getBlockY());
        jsonObject.addProperty("z", location.getBlockZ());
        jsonObject.addProperty("yaw", location.getYaw());
        jsonObject.addProperty("pitch", location.getPitch());
        return jsonObject;
    }

    @Override
    public Location deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String world_name = jsonObject.get("world_name").getAsString();
        int x = jsonObject.get("x").getAsInt();
        int y = jsonObject.get("y").getAsInt();
        int z = jsonObject.get("z").getAsInt();
        float yaw = jsonObject.get("yaw").getAsFloat();
        float pitch = jsonObject.get("pitch").getAsFloat();

        return new Location(Bukkit.getWorld(world_name), x, y, z, yaw, pitch);
    }
}
