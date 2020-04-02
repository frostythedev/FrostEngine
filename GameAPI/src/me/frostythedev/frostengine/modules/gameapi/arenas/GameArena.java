package gameapi.arenas;

import gameapi.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class GameArena implements Arena {

    private Minigame minigame;

    private int id;
    private String arenaName;
    private String worldName;
    private World world;

    private List<Location> spawnLocations;

    boolean enabled;
    boolean lobby;

    private Set<Integer> breakable;
    private Set<Integer> placeable;

    public GameArena(int id, Minigame minigame, String arenaName, String worldName, List<Location> spawnLocations, boolean enabled,
                     boolean lobby, Set<Integer> breakable, Set<Integer> placeable) {
        this.id = id;
        this.minigame = minigame;
        this.arenaName = arenaName;
        this.worldName = worldName;
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

    public boolean isInUse(){
        return getMinigame() != null;
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
}
