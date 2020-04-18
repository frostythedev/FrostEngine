package me.frostythedev.frostengine.legacy;

import me.frostythedev.frostengine.legacy.ui.SendConsole;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public abstract class BasePlugin extends JavaPlugin {

    private static BasePlugin instance;
    protected File pluginFile = null;

    public void onEnable() {
        new SendConsole(this);
        instance = this;
        this.pluginFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

        initialize();
    }

    public void onDisable() {
    }

    public void initialize() {
        loadDependencies(Bukkit.getPluginManager());
        registerManagers();
        registerCommands();
        registerSchedulers();
        registerListeners(Bukkit.getPluginManager());
        registerChannels();
        loadConfiguration();
    }

    public static BasePlugin getInstance() {
        return instance;
    }

    public File getFile() {
        return this.pluginFile;
    }

    public void registerListeners(PluginManager pm) {
    }

    public void registerSchedulers() {
    }

    public void registerCommands() {
    }

    public void registerManagers() {
    }

    public void registerChannels() {
    }

    public void loadDependencies(PluginManager pm) {
    }

    public void loadConfiguration() {
    }
}

