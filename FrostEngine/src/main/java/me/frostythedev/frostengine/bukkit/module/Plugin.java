package me.frostythedev.frostengine.bukkit.module;

import co.aikar.commands.BaseCommand;
import me.frostythedev.frostengine.legacy.cmds.api.Command;
import me.frostythedev.frostengine.bukkit.thread.RunnableManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

/**
 * Programmed by Tevin on 8/6/2016.
 */
public abstract class Plugin extends JavaPlugin implements ModuleBase {

    private Set<Listener> listeners;
    private Set<BaseCommand> commands;
    private Set<Module> childModules;
    private RunnableManager runnableManager;
    private ModuleLoader moduleLoader;

    /*@Override
    public void enable() {
        this.listeners = new HashSet<>();
        this.commands = new HashSet<>();
        this.childModules = new HashSet<>();

        this.runnableManager = new RunnableManager(this);

        this.moduleLoader = new ModuleLoader(this);
        this.moduleLoader.loadModules();

        onModuleEnable();

        if (getListeners() != null && !getListeners().isEmpty()) {
            ListenerUtils.enableAll(getListeners());
        }

        if (getCommands() != null && !getCommands().isEmpty()) {
            CommandUtils.enableAll(getCommands());
        }

        if (getChildModules() != null && !getChildModules().isEmpty()) {
            for (Module module : getChildModules()) {

                Locale.log("Starting child module '" + module.getModuleName() + "' ...");
                long now = System.currentTimeMillis();
                module.enable();
                Locale.log("Finished starting child module '" + module.getModuleName() + "' in [" + NumberUtil.elapse(now) + "m/s]");
            }
        }

        setLoaded(true);
    }*/

    public void setModuleLoader(ModuleLoader loader) {
        this.moduleLoader = loader;
    }

    @Override
    public ModuleLoader getModuleLoader() {
        return moduleLoader;
    }

    @Override
    public String getModuleName() {
        return getDescription().getName();
    }

    @Override
    public String getModuleDescription() {
        return getDescription().getDescription();
    }

    @Override
    public String getVersion() {
        return getDescription().getVersion();
    }

    @Override
    public String getAuthor() {
        return StringUtils.join(getDescription().getAuthors(), ',');
    }

    @Override
    public boolean isLoaded() {
        return isEnabled();
    }

    @Override
    public void setLoaded(boolean b) {
        setEnabled(b);
    }

    @Override
    public Set<Listener> getListeners() {
        return listeners;
    }

    @Override
    public Set<BaseCommand> getCommands() {
        return commands;
    }

    @Override
    public Set<Module> getChildModules() {
        return childModules;
    }


    @Override
    public void onModuleEnable() {
        this.listeners = new HashSet<>();
        this.commands = new HashSet<>();
        this.childModules = new HashSet<>();

        this.runnableManager = new RunnableManager(this);
    }

    @Override
    public void onModuleDisable() {

    }

    @Override
    public void onEnable() {
       this.enable();
    }

    @Override
    public void onDisable() {
        this.disable();
    }

    public URLClassLoader getURLClassLoader(){
        return (URLClassLoader) this.getClass().getClassLoader();
    }

    public RunnableManager getThreadManager() {
        return runnableManager;
    }
}
