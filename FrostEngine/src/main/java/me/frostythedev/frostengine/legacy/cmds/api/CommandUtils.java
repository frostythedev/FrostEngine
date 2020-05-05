package me.frostythedev.frostengine.legacy.cmds.api;

import co.aikar.commands.BaseCommand;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;

@Singleton
public class CommandUtils {

    @Inject static FEPlugin plugin;

    public static void enableAll(Collection<BaseCommand> collection){
        if(plugin != null && plugin.isEnabled() && collection != null && !collection.isEmpty()){
            for(BaseCommand cmd : collection){
                plugin.getManager().registerCommand(cmd);
            }
        }
    }
}
