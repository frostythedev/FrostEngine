package me.frostythedev.frostengine.modules.gameapi.arenas;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.modules.gameapi.Minigame;
import me.frostythedev.frostengine.bukkit.utils.items.ItemBuilder;
import me.frostythedev.frostengine.data.JsonConvertable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class GameArena implements Arena, JsonConvertable<GameArena> {

    private Minigame minigame;

    private int id;
    private String arenaName;
    private String worldName;
    private String minigameName;
    private World world;

    private List<Location> spawnLocations;

    boolean enabled;
    boolean lobby;

    private Set<Integer> breakable;
    private Set<Integer> placeable;

    public GameArena(int id, Minigame minigame, String arenaName, String worldName,
                     String minigameName, List<Location> spawnLocations, boolean enabled,
                     boolean lobby, Set<Integer> breakable, Set<Integer> placeable) {
        this.id = id;
        this.minigame = minigame;
        this.arenaName = arenaName;
        this.worldName = worldName;
        this.minigameName = minigameName;
        this.world = Bukkit.getWorld(worldName);
        this.spawnLocations = spawnLocations;
        this.enabled = enabled;
        this.lobby = lobby;
        this.breakable = breakable;
        this.placeable = placeable;
    }



    ///////////////////////////////////////////////
    // MADE METHODS
    ///////////////////////////////////////////////

    public boolean loadArena(){

        return false;
    }


    // MAKE MINGIAMES REPORT THE AMOUNT OF PLAYERS ARE WITHIN IT TO REFLECT ON ARENA
    // ICON
    public ItemStack getIcon(){
        ItemBuilder arenaIcon = new ItemBuilder(Material.WOOL);
        if(this.getMinigame() != null){
            if(isInUse()){
                arenaIcon.withCustomName("&c&l" + getArenaName());
                arenaIcon.withData((byte) 14);
                arenaIcon.withLore(
                        " ",
                        "&cMinigame: " + minigame.getDisplayName(),
                        "&cAuthor: " + minigame.getAuthor(),
                        "&cPlayers: 0" + "/" + minigame.getMaxPlayers(),
                        "&cGameState: " + minigame.getGameState().getDisplayName()

                        );
            }else{
                arenaIcon.withCustomName("&a&l" + getArenaName());
                arenaIcon.withData((byte) 5);
                arenaIcon.withLore(
                        " ",
                        "&aMinigame: " + minigame.getDisplayName(),
                        "&aAuthor: " + minigame.getAuthor(),
                        "&aPlayers: 0" + "/" + minigame.getMaxPlayers(),
                        "&aGameState: " + minigame.getGameState().getDisplayName()
                );

            }
        }
        return arenaIcon.build();
    }

    public boolean isUseable(){
        return !arenaName.equals("") && !spawnLocations.isEmpty();
    }

    public boolean isInUse(){
        if(getMinigame() != null){
            if(!getMinigame().getGameState().isJoinable()){
               return true;
            }
        }
        return false;
    }

    public boolean isRegisteredLocation(Location loc, boolean ignoreYawPitch){
        if(ignoreYawPitch){
          for(Location locs : this.getSpawnLocations()) {
              if(locs.getBlockX() == loc.getBlockX() && locs.getBlockY() == loc.getBlockY() &&
                      locs.getBlockZ() == loc.getBlockZ()){
                  return true;
              }
          }
        }else{
           return getSpawnLocations().contains(loc);
        }

        return false;
    }

    public Location getExactLocation(Location loc){
        if(isRegisteredLocation(loc, true)){
            for(Location locs : this.getSpawnLocations()) {
                if(locs.getBlockX() == loc.getBlockX() && locs.getBlockY() == loc.getBlockY() &&
                        locs.getBlockZ() == loc.getBlockZ()){
                    return locs;
                }
            }
        }
        return null;
    }

    public int getSize(){
        return spawnLocations.size();
    }

    public Location getSpawn(int index){
        return spawnLocations.get(index);
    }

    public Location getRandomSpawn(){
        return getSpawn(ThreadLocalRandom.current().nextInt(getSize()));
    }


    ///////////////////////////////////////////////
    // OVERRIDES
    ///////////////////////////////////////////////



    @Override
    public boolean isBreakable(Block block) {
        return breakable.contains(block.getTypeId());
    }

    @Override
    public boolean isPlaceable(Block block) {
        return placeable.contains(block.getTypeId());
    }


    ///////////////////////////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////////////////////////



    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Minigame getMinigame() {
        return minigame;
    }

    public void setMinigame(Minigame minigame) {
        this.minigame = minigame;
    }

    @Override
    public String getArenaName() {
        return arenaName;
    }

    public void setArenaName(String arenaName) {
        this.arenaName = arenaName;
    }

    @Override
    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    @Override
    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public List<Location> getSpawnLocations() {
        return spawnLocations;
    }

    public void setSpawnLocations(List<Location> spawnLocations) {
        this.spawnLocations = spawnLocations;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isLobby() {
        return lobby;
    }

    public void setLobby(boolean lobby) {
        this.lobby = lobby;
    }

    public Set<Integer> getBreakable() {
        return breakable;
    }

    public void setBreakable(Set<Integer> breakable) {
        this.breakable = breakable;
    }

    public Set<Integer> getPlaceable() {
        return placeable;
    }

    public void setPlaceable(Set<Integer> placeable) {
        this.placeable = placeable;
    }

    public String getMinigameName() {
        return minigameName;
    }

    public void setMinigameName(String minigameName) {
        this.minigameName = minigameName;
    }

    @Override
    public JsonObject serialize() {
        JsonObject jsonObject = new JsonObject();

        int id = getId();
        String arenaName = getArenaName();
        String worldName = getWorldName();
        List<Location> spawnLocations = getSpawnLocations();
        boolean enabled = isEnabled();
        boolean lobby = isLobby();
        Set<Integer> breakable = getBreakable();
        Set<Integer> placeable = getPlaceable();


        jsonObject.addProperty("id", id);
        jsonObject.addProperty("arenaName", arenaName);
        jsonObject.addProperty("worldName", worldName);
        jsonObject.addProperty("minigameName", getMinigameName());
        jsonObject.addProperty("enabled", enabled);
        jsonObject.addProperty("lobby", lobby);

        List<String> spawns = Lists.newArrayList();
        if (spawnLocations != null && !spawnLocations.isEmpty()) {
            for (Location loc : spawnLocations) {
                String location = FEPlugin.getGson().toJson(loc);
                spawns.add(location);
            }
        }

        String spawnData = "";
        if(!spawns.isEmpty()){
            for(String str : spawns){
                if(spawnData.equals("")){
                    spawnData+= str;
                }else{
                    spawnData+=";" + str;
                }
            }
        }
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

        return jsonObject;
    }

    @Override
    public GameArena deserialize(JsonElement element) {
        JsonObject jsonObject = element.getAsJsonObject();

        int id = jsonObject.get("id").getAsInt();
        String arenaName = jsonObject.get("arenaName").getAsString();
        String worldName = jsonObject.get("worldName").getAsString();
        String minigameName = jsonObject.get("minigameName").getAsString();
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

        setId(id);
        setArenaName(arenaName);
        setWorld(Bukkit.getWorld(worldName));
        setSpawnLocations(spawns);
        setEnabled(enabled);
        setLobby(lobby);
        setBreakable(breakable);
        setPlaceable(placeable);
        setMinigameName(minigameName);
        return this;
    }
}
