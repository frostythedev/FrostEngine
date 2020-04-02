package me.frostythedev.frostengine.modules.achievements.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    private Map<UUID, AchievementPlayer> players;

    public PlayerManager() {
        this.players = new HashMap<>();
    }

    public void loadPlayer(UUID uuid, AchievementPlayer player) {
        players.put(uuid, player);
    }

    public void loadPlayer(Player p, AchievementPlayer player){
        this.loadPlayer(p.getUniqueId(), player);
    }

    public void unloadPlayer(UUID uuid){
        if(isLoaded(uuid)){
            players.remove(uuid);
        }
    }

    public void unloadPlayer(Player p){
       this.unloadPlayer(p.getUniqueId());
    }

    public AchievementPlayer getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public AchievementPlayer getPlayer(Player p){
        return getPlayer(p.getUniqueId());
    }

    public boolean isLoaded(UUID uuid) {
        return players.containsKey(uuid);
    }

    public boolean isLoaded(Player p){
        return isLoaded(p.getUniqueId());
    }

    public Map<UUID, AchievementPlayer> getPlayers() {
        return players;
    }
}
