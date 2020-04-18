package me.frostythedev.frostengine.bukkit.module;

import me.frostythedev.frostengine.bukkit.cmd.api.Command;
import me.frostythedev.frostengine.bukkit.event.api.AbstractListener;
import org.bukkit.event.Listener;

import java.util.Set;

/**
 * Programmed by Tevin on 8/6/2016.
 */
public interface IModule {

    String getModuleName();
    String getModuleDescription();
    String getVersion();
    String getAuthor();
    boolean isLoaded();
    void setLoaded(boolean b);
    Set<Listener> getListeners();
    Set<Command> getCommands();
    Set<Module> getChildModules();

    default void addListener(AbstractListener listener){}

    default void addCommand(Command command){}

    default void addChildModule(Module module){}

}
