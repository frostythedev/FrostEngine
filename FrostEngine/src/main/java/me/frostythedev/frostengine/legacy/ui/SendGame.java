package me.frostythedev.frostengine.legacy.ui;

import me.frostythedev.frostengine.legacy.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class SendGame
{
    public static void toPlayer(String message, Player player)
    {
        message = ColorUtils.toColors(message);
        player.sendMessage(message);
    }

    public static void toPermission(String message, String permission)
    {
        message = ColorUtils.toColors(message);
        Bukkit.broadcast(message, permission);
    }

    public static void toServer(String message)
    {
        message = ColorUtils.toColors(message);
        Bukkit.broadcastMessage(message);
    }

    public static void toWorld(String message, World world)
    {
        message = ColorUtils.toColors(message);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld().equals(world)) {
                player.sendMessage(message);
            }
        }
    }

    public static void toWorldGroup(String message, World world)
    {
        message = ColorUtils.toColors(message);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if ((player.getWorld().equals(world)) ||
                    (player.getWorld().equals(
                            Bukkit.getWorld(world.getName() + "_nether"))) ||
                    (player.getWorld().equals(
                            Bukkit.getWorld(world.getName() + "_the_end")))) {
                player.sendMessage(message);
            }
        }
    }
}

