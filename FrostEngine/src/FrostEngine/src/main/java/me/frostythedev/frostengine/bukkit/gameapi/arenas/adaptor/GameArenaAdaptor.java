package me.frostythedev.frostengine.bukkit.gameapi.arenas.adaptor;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import me.frostythedev.frostengine.bukkit.gameapi.arenas.GameArena;
import me.frostythedev.frostengine.data.JsonAdaptor;
import me.frostythedev.frostengine.bukkit.gameapi.arenas.NullGameArena;

import java.lang.reflect.Type;

public class GameArenaAdaptor implements JsonAdaptor<GameArena> {
    @Override
    public GameArena deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        /*JsonObject jsonObject = jsonElement.getAsJsonObject();

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
            String breakables = jsonObject.get("breakables").getAsString();
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
            String placeables = jsonObject.get("placeables").getAsString();
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

        return new GameArena(id, null, arenaName, worldName, spawns, enabled, lobby, breakable, placeable);*/

        return new NullGameArena().deserialize(jsonElement);
    }

    @Override
    public JsonElement serialize(GameArena gameArena, Type type, JsonSerializationContext jsonSerializationContext) {
        /*JsonObject jsonObject = new JsonObject();

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

        jsonObject.addProperty("breakables", breakables);

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

        return jsonObject;*/
        return gameArena.serialize();
    }
}
