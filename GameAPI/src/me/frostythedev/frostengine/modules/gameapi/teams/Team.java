package gameapi.teams;

import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public interface Team {

    String getName();

    String getDisplayName();

    default void setDisplayName(String name){

    }

    Set<UUID> getPlayers();

    default boolean addPlayer(Player player){
        return false;
    }

    default boolean removePlayer(Player player){
        return false;
    }

    default boolean containsPlayer(Player player){
        return getPlayers().contains(player.getUniqueId());
    }

}
