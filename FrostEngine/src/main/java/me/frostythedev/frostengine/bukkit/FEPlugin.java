package me.frostythedev.frostengine.bukkit;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.BukkitCommandManager;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Injector;
import me.frostythedev.frostengine.bukkit.adaptors.ItemstackAdaptor;
import me.frostythedev.frostengine.bukkit.adaptors.LocationAdaptor;
import me.frostythedev.frostengine.bukkit.commands.ModuleCommand;
import me.frostythedev.frostengine.bukkit.module.ModuleHandler;
import me.frostythedev.frostengine.bukkit.statistics.StatisticManager;
import me.frostythedev.frostengine.bukkit.statistics.TrackableStatistic;
import me.frostythedev.frostengine.bukkit.thread.RunnableManager;
import me.frostythedev.frostengine.bukkit.utils.LogUtils;
import me.frostythedev.frostengine.bukkit.utils.hologram.Hologram;
import me.frostythedev.frostengine.bukkit.utils.hologram.HologramManager;
import me.frostythedev.frostengine.bukkit.utils.hologram.Holograms;
import me.frostythedev.frostengine.bukkit.utils.hologram.adaptor.HologramAdaptor;
import me.frostythedev.frostengine.data.JsonAdaptor;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;

public class FEPlugin extends JavaPlugin {

//  public String defaultModuleStorageFolder = this.getDataFolder().getAbsolutePath() + File.separator + "modules";

    private Map<Class<?>, JsonAdaptor> adaptors;

    /*private HologramManager hologramManager;
    private StatisticManager statisticManager;*/
    private RunnableManager runnableManager;
    private BukkitCommandManager commandManager;

    private Injector injector;

    @Inject
    ModuleCommand moduleCommand;
    @Inject
    private ModuleHandler moduleHandler;

    // @Inject ModuleGameAPI gameAPI;

    @Override
    public void onEnable() {
        LogUtils.init(this);

        this.runnableManager = new RunnableManager();

        this.commandManager = new BukkitCommandManager(this);

        FEBinderModule sbm = new FEBinderModule(this, commandManager,
                new StatisticManager(), new HologramManager(),
                ModuleHandler.getOfferedModules());

        this.injector = sbm.createInjector();
        this.injector.injectMembers(this);

        this.getCommandManager().registerCommand(moduleCommand);


        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
        }

        this.adaptors = Maps.newHashMap();

        getAdaptors().put(ItemStack.class, new ItemstackAdaptor());
        getAdaptors().put(Location.class, new LocationAdaptor());
        getAdaptors().put(Hologram.class, new HologramAdaptor());

        moduleHandler.enable();

        Holograms.registerHandler(this);
    }

    public Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        for (Map.Entry<Class<?>, JsonAdaptor> entry : adaptors.entrySet()) {
            builder.registerTypeAdapter(entry.getKey(), entry.getValue());
        }
        return builder.create();
    }


    private void registerCommands(BaseCommand... commands) {
        for (BaseCommand cmd : commands) {
            this.getCommandManager().registerCommand(cmd);
        }
    }

    public void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            this.getServer().getPluginManager().registerEvents(listener, this);
        }
    }


    public BukkitCommandManager getCommandManager() {
        return commandManager;
    }

    public Map<Class<?>, JsonAdaptor> getAdaptors() {
        return adaptors;
    }

    /*public HologramManager getHologramManager() {
        return hologramManager;
    }

    public StatisticManager getStatisticManager() {
        return statisticManager;
    }*/

    public RunnableManager getRunnableManager() {
        return runnableManager;
    }

    public Injector getInjector() {
        return injector;
    }

    /*public NPCManager getNpcManager() {
        return npcManager;
    }*/

}
