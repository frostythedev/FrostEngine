package me.frostythedev.frostengine.bukkit.debug;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Debugger {

    private static JavaPlugin plugin;

    public static void init(JavaPlugin plugin){
        Debugger.plugin = plugin;
    }

    public static void debug(String message) {
        plugin.getLogger().severe(message);
    }

    public static void log(Level severe, String s, Exception e) {
        plugin.getLogger().log(severe, s, e);
    }

    public static void log(Level severe, String s) {
        plugin.getLogger().log(severe, s);
    }

    public static void info(String s) {
        plugin.getLogger().info(s);
    }
}
