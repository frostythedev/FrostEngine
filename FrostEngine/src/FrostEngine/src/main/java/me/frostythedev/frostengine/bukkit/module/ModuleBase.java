package me.frostythedev.frostengine.bukkit.module;


import me.frostythedev.frostengine.bukkit.cmd.api.Command;
import me.frostythedev.frostengine.bukkit.cmd.api.CommandUtils;
import me.frostythedev.frostengine.bukkit.event.api.AbstractListener;
import me.frostythedev.frostengine.bukkit.event.api.ListenerUtils;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.bukkit.utils.NumberUtil;
import org.bukkit.event.Listener;

/**
 * Programmed by Tevin on 7/18/2016.
 */
public interface ModuleBase extends IModule {

    void onModuleEnable();

    void onModuleDisable();

    default void addListener(AbstractListener listener) {
        if (!isLoaded()) return;
        getListeners().add(listener);
    }

    default void registerListeners(Listener... listeners) {
        if (!isLoaded()) return;
        for (Listener listener : listeners) {
            getListeners().add(listener);
        }
    }

    default void addCommand(Command command) {
        if (!isLoaded()) return;
        getCommands().add(command);
    }

    default void addChildModule(Module module) {
        if (!isLoaded()) return;
        getChildModules().add(module);
    }

    default void enable() {
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
    }

    default void disable() {
        onModuleDisable();

        if (getListeners() != null && !getListeners().isEmpty()) {
            ListenerUtils.disableAll(getListeners());
        }

        setLoaded(false);
    }

}
