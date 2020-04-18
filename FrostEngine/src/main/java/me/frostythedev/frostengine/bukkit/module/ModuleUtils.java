package me.frostythedev.frostengine.bukkit.module;

import me.frostythedev.frostengine.bukkit.messaging.Locale;
import org.bukkit.command.CommandSender;

/**
 * Programmed by Tevin on 8/8/2016.
 */
public class ModuleUtils {

    public static void sendModuleInfo(Module module, CommandSender sender) {
        sender.sendMessage(" ");
        if(module != null){
            sender.sendMessage(Locale.toColors("&aModule Details: &n" + module.getModuleName()));
            sender.sendMessage(Locale.toColors("&aName: &7" + module.getModuleName()));
            sender.sendMessage(Locale.toColors("&aDescription: &7" + module.getModuleDescription()));
            sender.sendMessage(Locale.toColors("&aVersion: &7" + module.getVersion()));
            sender.sendMessage(Locale.toColors("&aAuthors: &7" + module.getAuthor()));
            sender.sendMessage(Locale.toColors("&aLoaded Commands: &7" + module.getCommands().size()));
            sender.sendMessage(Locale.toColors("&aChild Modules: &7" + module.getChildModules().size()));
        }else{
            sender.sendMessage("Null module");
        }
    }
}
