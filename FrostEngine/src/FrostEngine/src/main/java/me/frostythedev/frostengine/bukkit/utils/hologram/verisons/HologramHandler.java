package me.frostythedev.frostengine.bukkit.utils.hologram.verisons;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface HologramHandler {

    int spawn(Player player, String line, Location loc);

    void destroy(Player player, int id);
}
