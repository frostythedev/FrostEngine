package me.frostythedev.frostengine.legacy.ui;


import me.frostythedev.frostengine.legacy.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;

public class SendConsole
{
    static boolean enableLogging = false;
    static String prefix;
    static Plugin plugin = null;

    public SendConsole(Plugin plugin)
    {
        plugin = plugin;
        prefix = "[" + plugin.getName() + "] ";
    }

    public static void message(String message)
    {
        ConsoleCommandSender console = plugin.getServer().getConsoleSender();
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void info(String message)
    {
        Bukkit.getLogger().info(prefix + message);
    }

    public static void warning(String message)
    {
        Bukkit.getLogger().warning(prefix + message);
    }

    public static void severe(String message)
    {
        message = ColorUtils.removeColors(message);
        Bukkit.getLogger().severe(prefix + message);
    }
}

