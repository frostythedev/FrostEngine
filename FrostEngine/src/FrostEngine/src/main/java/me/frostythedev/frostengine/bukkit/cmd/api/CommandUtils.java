package me.frostythedev.frostengine.bukkit.cmd.api;

import me.frostythedev.frostengine.bukkit.FEPlugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;

public class CommandUtils {

    public static void enableAll(JavaPlugin plugin, Collection<Command> collection){
       if(plugin != null && plugin.isEnabled() && collection != null && !collection.isEmpty()){
           for(Command cmd : collection){
               plugin.getCommand(cmd.getName()).setExecutor(cmd);
           }
       }
    }

    public static void enableAll(Collection<Command> collection){
        enableAll(FEPlugin.get(), collection);
    }
}
