package me.frostythedev.frostengine.bukkit.event.api;

import me.frostythedev.frostengine.bukkit.FEPlugin;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.Collection;

/**
 * Programmed by Tevin on 7/18/2016.
 */
public class ListenerUtils {

    public static void enableAll(Collection<Listener> collection){
        for(Listener listener : collection){
            FEPlugin.get().getServer().getPluginManager().registerEvents(listener, FEPlugin.get());
        }
    }

    public static void disableAll(Collection<Listener> collection){
        collection.forEach(HandlerList::unregisterAll);
    }
}
