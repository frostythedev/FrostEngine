package me.frostythedev.frostengine.bukkit.utils;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtils {
    private static Logger logger;

    public static void init(String name) {
        logger = Logger.getLogger(name);
    }

    public static void init(Plugin plugin) {
        logger = plugin.getLogger();

        for (Handler h : logger.getParent().getHandlers()) {
            logger.addHandler(h);
        }
        logger.setUseParentHandlers(false);
    }

    public static Level getLogLevel() {
        return logger.getLevel();
    }

    public static void setLogLevel(Level level) {
        logger.setLevel(level);
        for (Handler h : logger.getHandlers()) {
            h.setLevel(level);
        }
    }

    public static void setLogLevel(String val) {
        setLogLevel(Level.parse(val.toUpperCase()));
    }

    public static void log(Level level, String message) {
        logger.log(level, message);
    }

    public static void fine(String message) {
        logger.fine(message);
    }

    public static void finer(String message) {
        logger.finer(message);
    }

    public static void finest(String message) {
        logger.finest(message);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void warning(String message) {
        logger.warning(message);
    }

    public static void severe(String message) {
        logger.severe(message);
    }

    public static void warning(String message, Exception err) {
        if (err == null) {
            warning(message);
        } else {
            logger.log(Level.WARNING, getMsg(message, err));
        }
    }

    public static void severe(String message, Exception err) {
        if (err == null) {
            severe(message);
        } else {
            logger.log(Level.SEVERE, getMsg(message, err));
        }
    }

    private static String getMsg(String message, Exception e) {
        return message == null ? e.getMessage() : ChatColor.stripColor(message);
    }
}