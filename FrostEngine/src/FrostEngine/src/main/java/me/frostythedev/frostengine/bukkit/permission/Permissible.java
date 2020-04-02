package me.frostythedev.frostengine.bukkit.permission;

import org.bukkit.entity.Player;

/**
 * An object that has permission data attached.
 */
public interface Permissible {
    public String getPermission();

    public boolean hasPermission(Player p);
}
