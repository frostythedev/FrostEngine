package me.frostythedev.frostengine.bukkit;

import co.aikar.commands.BukkitCommandManager;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import me.frostythedev.frostengine.bukkit.module.Module;
import me.frostythedev.frostengine.bukkit.statistics.StatisticManager;
import me.frostythedev.frostengine.bukkit.utils.hologram.HologramManager;

import java.util.Map;

public class FEBinderModule extends AbstractModule {

    private FEPlugin plugin;
    private BukkitCommandManager commandManager;
    private StatisticManager statisticManager;
    private HologramManager hologramManager;
    private Map<Class<Module>, Module> offeredModules;

    // This is also dependency injection, but without any libraries/frameworks since we can't use those here yet.


    public FEBinderModule(FEPlugin plugin, BukkitCommandManager commandManager,
                          StatisticManager statisticManager, HologramManager hologramManager,
                          Map<Class<Module>, Module> offeredModules) {
        this.plugin = plugin;
        this.commandManager = commandManager;
        this.statisticManager = statisticManager;
        this.hologramManager = hologramManager;
        this.offeredModules = offeredModules;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        // Here we tell Guice to use our plugin instance everytime we need it
        this.bind(FEPlugin.class).toInstance(this.plugin);
        this.bind(BukkitCommandManager.class).toInstance(this.commandManager);
        this.bind(StatisticManager.class).toInstance(this.statisticManager);
        this.bind(HologramManager.class).toInstance(this.hologramManager);

        offeredModules.forEach((key, value) -> bind(key).toInstance(value));
    }
}
