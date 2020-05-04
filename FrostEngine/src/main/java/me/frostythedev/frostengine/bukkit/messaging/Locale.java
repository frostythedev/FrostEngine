package me.frostythedev.frostengine.bukkit.messaging;

import com.google.common.collect.Lists;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Programmed by Tevin on 8/2/2016.
 */
public class Locale {

    private static HashMap<String, String> messages;

    public static final String ERROR_PREF = "&4&l>> &c";
    public static final String SUCCESS_PREF = "&2&l>> &a";

    public static final String SQL_TABLE_PREFIX = FEPlugin.get().getConfig().getString("MySQL.Table-prefix");

    public static final String ERROR_MESSAGE = ERROR_PREF + "&cAn error occurred please contact an administrator for more information.";
    public static final String ERROR_ELEMENT_DUPLICATION = ERROR_PREF + "&cAn error occurred as this element already exists and cannot co-exist anymore.";
    public static final String ERROR_HOLOGRAM_DUPLICATION = ERROR_PREF + "&cAn error occurred as this hologram already exists and cannot co-exist anymore.";
    public static final String ERROR_CANNOT_AS_CONSOLE = ERROR_PREF + "&cYou must be a player to complete this action.";

    public static final String GENERAL_TARGET_UNKNOWN = ERROR_PREF + "&cCould not find a target with the specified name.";
    public static final String GENERAL_MODULE_NOT_FOUND = ERROR_PREF + "&cCould not find a module with the specified name.";

    public static final String COMMAND_INVALID_ARGS = ERROR_PREF + "&cInvalid arguments.";
    public static final String COMMAND_NO_PERMS = ERROR_PREF + "&cYou are not allowed to do this.";
    public static final String COMMAND_UNKNOWN_ARGS = ERROR_PREF + "&cCould not find argument with the specified name.";

    public static void log(String message) {
        Bukkit.getLogger().info("[" + FEPlugin.get().getDescription().getName() + "] " + message);
    }

    public static void message(CommandSender sender, String message) {
        sender.sendMessage(toColors(message));
    }

    public static void messagef(CommandSender sender, String message, Object... args){
        sender.sendMessage(String.format(toColors(message), args));
    }

    public static void blankLine(CommandSender sender){
        message(sender, " ");
    }

    public static void error(CommandSender sender, String message) {
        message(sender, ERROR_PREF + message);
    }

    public static void success(CommandSender sender, String message) {
        message(sender, SUCCESS_PREF + message);
    }

    public static String toColors(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static String toColors(String input, Object... vars) {
        return String.format(toColors(input), vars);
    }

    public static String toColors(String input, String[] vars, String... value) {
        Validate.isTrue(value.length >= vars.length, "Missing values for " + vars.length + " arguments.");
        for (int i = 0; i < vars.length; i++) {
            input.replace("%" + vars[i], value[i]);
        }
        return input;
    }

    /*public static String toColors(Lang lang, String... values) {
        return toColors(lang.getValue(), lang.getVariables(), values);
    }*/

    public static List<String> toColors(List<String> lines) {
        List<String> l = Lists.newArrayList();
        for (String str : lines) {
            l.add(toColors(str));
        }
        return l;
    }

    public static String stripColors(String input) {
        return ChatColor.stripColor(input);
    }

    public static void broadcast(String message) {
        Bukkit.getOnlinePlayers().forEach(ps -> message(ps, toColors(message)));
    }

    public static void broadcast(String message, Object... vars) {
        Bukkit.getOnlinePlayers().forEach(ps -> message(ps, toColors(message, vars)));
    }

    public static void broadcast(String message, String[] vars, String... value) {
        Bukkit.broadcastMessage(toColors(message, vars, value));
    }

/*
    public static void broadcast(Lang lang, String... values) {
        broadcast(lang.getValue(), lang.getVariables(), values);
    }
*/

    public static void broadcast(String permission, String message) {
        Bukkit.getOnlinePlayers().stream().filter(ps -> ps.hasPermission(permission)).forEach(p -> message(p, message));
    }

    public static String from(String path) {
        if (messages.containsKey(path)) {
            return ChatColor.translateAlternateColorCodes('&', messages.get(path));
        } else {
            return from("fe_error");
        }
    }

    private static String load(String identif, String value) {
        if (messages.containsKey(identif)) {
            messages.replace(identif, value);
        }
        messages.put(identif, value);
        return value;
    }

    public static void write(File file) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, String> entry : messages.entrySet()) {
            config.set(entry.getKey(), entry.getValue());
        }
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static Optional<HashMap<String, String>> fromFile(File file) {
        if (!file.getParentFile().exists()) {
            return Optional.empty();
        }
        if (!file.exists()) {
            return Optional.empty();
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        HashMap<String, String> messages = new HashMap<>();

        for (String keys : config.getKeys(false)) {
            if (config.isConfigurationSection(keys)) {
                for (String str : config.getConfigurationSection(keys).getKeys(false)) {
                    String path = keys + "." + str;
                    String value = config.getString(path);
                    messages.put(path, value);
                }
            } else {
                if (config.getString(keys) != null) {
                    messages.put(keys, config.getString(keys));
                }
            }
        }

        return Optional.of(messages);
    }
}
