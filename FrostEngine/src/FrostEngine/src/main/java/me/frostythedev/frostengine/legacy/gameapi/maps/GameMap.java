package me.frostythedev.frostengine.legacy.gameapi.maps;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.frostythedev.frostengine.bukkit.utils.location.LocationUtil;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.data.JsonConvertable;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;

public class GameMap implements Map, JsonConvertable<GameMap>{

    private int id;
    private String name;
    private String displayName;
    private boolean enabled;
    private boolean inUse;
    private Location lobbyLocation;
    private List<Location> spawnLocations;

    public GameMap(int id, String name, String displayName, boolean enabled, boolean inUse, Location lobbyLocation,
                   List<Location> spawnLocations) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.enabled = enabled;
        this.inUse = inUse;
        this.lobbyLocation = lobbyLocation;
        this.spawnLocations = spawnLocations;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isUsed() {
        return inUse;
    }

    @Override
    public Location getLobbyLocation() {
        return lobbyLocation;
    }

    @Override
    public List<Location> getSpawnLocations() {
        return spawnLocations;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public void setLobbyLocation(Location lobbyLocation) {
        this.lobbyLocation = lobbyLocation;
    }

    public void setSpawnLocations(List<Location> spawnLocations) {
        this.spawnLocations = spawnLocations;
    }

    @Override
    public JsonObject serialize() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", getId());
        jsonObject.addProperty("name", getName());
        jsonObject.addProperty("displayName", getDisplayName());
        jsonObject.addProperty("enabled", isEnabled());
        jsonObject.addProperty("used", isUsed());
        jsonObject.addProperty("lobbyLocation", (getLobbyLocation() != null ? FEPlugin.getGson().toJson(getLobbyLocation()) : ""));
        jsonObject.addProperty("spawnLocations", LocationUtil.listOfLocationsToString(getSpawnLocations()));
        return jsonObject;
    }

    @Override
    public GameMap deserialize(JsonElement element) {
        JsonObject jsonObject = element.getAsJsonObject();
        int id = jsonObject.get("id").getAsInt();
        String name = jsonObject.get("name").getAsString();
        String displayName = jsonObject.get("displayName").getAsString();
        boolean enabled = jsonObject.get("enabled").getAsBoolean();
        boolean used = jsonObject.get("used").getAsBoolean();

        Location lobbyLocation = (jsonObject.get("lobbyLocation").getAsString().equals("") ?
        new Location(Bukkit.getWorld("world"), 0,275,0)
                : FEPlugin.getGson().fromJson(jsonObject.get("lobbyLocation").getAsString(), Location.class));

        List<Location> spawnLocations = LocationUtil.
                listOfLocationsFromString(jsonObject.get("spawnLocations").getAsString());

        return new GameMap(id, name, displayName, enabled, used, lobbyLocation, spawnLocations);
    }

}
