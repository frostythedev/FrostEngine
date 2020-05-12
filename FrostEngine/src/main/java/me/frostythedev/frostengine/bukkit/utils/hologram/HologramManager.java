package me.frostythedev.frostengine.bukkit.utils.hologram;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Singleton
public class HologramManager {

    private Map<UUID, List<Hologram>> playerHolograms;

    public HologramManager() {
        this.playerHolograms = Maps.newHashMap();
    }

    public Hologram getHologram(Player player, String name){
        if(getHolograms(player) != null){
            for(Hologram holo : getHolograms(player)){
                if(holo.getName().equalsIgnoreCase(name)){
                    return holo;
                }
            }
        }
        return null;
    }

    public List<Hologram> getHolograms(Player player){
        if(playerHolograms.containsKey(player.getUniqueId())){
            return playerHolograms.get(player.getUniqueId());
        }
        return null;
    }

    public Map<UUID, List<Hologram>> getPlayerHolograms() {
        return playerHolograms;
    }
}
