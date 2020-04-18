package gameapi.adaptors;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.*;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.utilities.StringUtil;
import me.frostythedev.frostengine.data.JsonAdaptor;
import gameapi.arenas.GameArena;
import org.bukkit.Location;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

public class GameArenaAdaptor implements JsonAdaptor<GameArena> {
    @Override
    public GameArena deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int id = jsonObject.get("id").getAsInt();
        String arenaName = jsonObject.get("arenaName").getAsString();
        String worldName = jsonObject.get("worldName").getAsString();
        boolean enabled = jsonObject.get("enabled").getAsBoolean();
        boolean lobby = jsonObject.get("lobby").getAsBoolean();
        List<Location> spawns = Lists.newArrayList();
        Set<Integer> breakable = Sets.newHashSet();
        Set<Integer> placeable = Sets.newHashSet();

        {
            String spawnsData = jsonObject.get("spawns").getAsString();

            if(!spawnsData.contains(";")){
                spawns.add(FEPlugin.getGson().fromJson(spawnsData, Location.class));
            }else{
                for(String parts : spawnsData.split(";")){
                    Location loc = FEPlugin.getGson().fromJson(parts, Location.class);
                    if(loc != null){
                        spawns.add(loc);
                    }
                }
            }
        }

        {
            String breakables = jsonObject.get("breakable").getAsString();
            if(!breakables.equals("")){
             if(!breakables.contains(";")){
                 breakable.add(Integer.valueOf(breakables));
             }else{
                 for(String part : breakables.split(";")){
                     breakable.add(Integer.valueOf(part));
                 }
             }
            }
        }

        {
            String placeables = jsonObject.get("placeable").getAsString();
            if(!placeables.equals("")){
                if(!placeables.contains(";")){
                    breakable.add(Integer.valueOf(placeables));
                }else{
                    for(String part : placeables.split(";")){
                        placeable.add(Integer.valueOf(part));
                    }
                }
            }
        }

        return new GameArena(id, null, arenaName, worldName, spawns, enabled, lobby, breakable, placeable);
    }

    @Override
    public JsonElement serialize(GameArena gameArena, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        int id = gameArena.getId();
        String arenaName = gameArena.getArenaName();
        String worldName = gameArena.getWorldName();
        List<Location> spawnLocations = gameArena.getSpawnLocations();
        boolean enabled = gameArena.isEnabled();
        boolean lobby = gameArena.isLobby();
        Set<Integer> breakable = gameArena.getBreakable();
        Set<Integer> placeable = gameArena.getPlaceable();


        jsonObject.addProperty("id", id);
        jsonObject.addProperty("arenaName", arenaName);
        jsonObject.addProperty("worldName", worldName);
        jsonObject.addProperty("enabled", enabled);
        jsonObject.addProperty("lobby", lobby);

        List<String> spawns = Lists.newArrayList();
        if (spawnLocations != null && !spawnLocations.isEmpty()) {
            for (Location loc : spawnLocations) {
                String location = FEPlugin.getGson().toJson(loc);
                spawns.add(location);
            }
        }
        String spawnData = StringUtil.joinString((String[]) spawns.toArray(), ";");
        jsonObject.addProperty("spawns", spawnData);

        String breakables = "";
        if (!breakable.isEmpty()) {
            for (int i : breakable) {
                if (breakables.equals("")) {
                    breakables += String.valueOf(i);
                } else {
                    breakables += ";" + String.valueOf(i);
                }
            }
        }

        jsonObject.addProperty("breakable", breakables);

        String placeables = "";
        if (!placeables.isEmpty()) {
            for (int i : placeable) {
                if (placeables.equals("")) {
                    placeables += String.valueOf(i);
                } else {
                    placeables += ";" + String.valueOf(i);
                }
            }
        }

        jsonObject.addProperty("placeables", placeables);

        return jsonObject;
    }
}
