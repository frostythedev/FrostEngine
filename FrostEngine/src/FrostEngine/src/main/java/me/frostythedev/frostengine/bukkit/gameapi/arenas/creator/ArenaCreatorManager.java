package me.frostythedev.frostengine.bukkit.gameapi.arenas.creator;

import com.google.common.collect.Sets;
import me.frostythedev.frostengine.bukkit.Messages;
import me.frostythedev.frostengine.bukkit.gameapi.arenas.NullGameArena;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import org.bukkit.entity.Player;

import java.util.Set;

public class ArenaCreatorManager {

    private Set<ArenaCreator> creators;

    public ArenaCreatorManager() {
        this.creators = Sets.newHashSet();
    }

    public boolean isCreator(Player player){
        for(ArenaCreator c : creators){
            if(c.getUuid().equals(player.getUniqueId())){
                return true;
            }
        }
        return false;
    }

    public boolean enableCreator(Player player){
        if(isCreator(player)){
            return false;
        }else{

            ArenaCreator creator = new ArenaCreator(player);
            creator.setArena(new NullGameArena());
            creators.add(creator);
            Locale.message(player, Messages.MESSAGE_PREFIX + Messages.ACREATOR_ENABLED);
            return true;
        }
    }

    public ArenaCreator getArena(Player player) {
        if(isCreator(player)){
            for(ArenaCreator c : creators){
                if(c.getUuid().equals(player.getUniqueId())){
                    return c;
                }
            }
        }
        return null;
    }
}
