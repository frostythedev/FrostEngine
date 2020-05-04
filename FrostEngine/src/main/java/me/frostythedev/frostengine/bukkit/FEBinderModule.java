package me.frostythedev.frostengine.bukkit;

import co.aikar.commands.BukkitCommandManager;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class FEBinderModule extends AbstractModule {

    private FEPlugin plugin;
    private BukkitCommandManager manager;


    // This is also dependency injection, but without any libraries/frameworks since we can't use those here yet.
    public FEBinderModule(FEPlugin plugin, BukkitCommandManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        // Here we tell Guice to use our plugin instance everytime we need it
        this.bind(FEPlugin.class).toInstance(this.plugin);
        this.bind(BukkitCommandManager.class).toInstance(this.manager);
    }
}
