package me.frostythedev.frostengine.bukkit.gameapi.arenas;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.List;

public interface Arena {

    int getId();

    String getArenaName();

    String getWorldName();

    World getWorld();

    List<Location> getSpawnLocations();

    boolean isEnabled();

    boolean isLobby();

    boolean isBreakable(Block block);

    boolean isPlaceable(Block block);

}
