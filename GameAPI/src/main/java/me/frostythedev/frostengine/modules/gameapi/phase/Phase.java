package me.frostythedev.frostengine.modules.gameapi.phase;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public interface Phase {

    void handle(Player player);

    default void handle(Event event, Player player){

    }

    default void unhandle(Player player){

    }
}
