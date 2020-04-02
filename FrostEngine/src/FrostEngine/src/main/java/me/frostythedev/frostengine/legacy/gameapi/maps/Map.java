package me.frostythedev.frostengine.legacy.gameapi.maps;

import org.bukkit.Location;

import java.util.List;

public interface Map {
    int getId();
    String getName();
    String getDisplayName();
    boolean isEnabled();
    boolean isUsed();
    Location getLobbyLocation();
    List<Location> getSpawnLocations();
}
