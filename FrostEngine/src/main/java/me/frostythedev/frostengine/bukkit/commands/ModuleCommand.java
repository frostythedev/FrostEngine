package me.frostythedev.frostengine.bukkit.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.google.inject.Inject;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.bukkit.module.Module;
import me.frostythedev.frostengine.bukkit.module.ModuleAPI;
import me.frostythedev.frostengine.bukkit.module.ModuleUtils;
import me.frostythedev.frostengine.bukkit.utils.LogUtils;
import org.bukkit.command.CommandSender;

@CommandAlias("module|mod")
@CommandPermission("frostengine.admin.module")
public class ModuleCommand extends BaseCommand {

    @Inject private FEPlugin plugin;

    @Default
    @HelpCommand
    public void onHelp(CommandSender sender){
        Locale.message(sender, "&a&lFrostEngine Module Help");
        Locale.messagef(sender, "&e>> /module %s %s - %s", "list", "", "Lists all installed modules");
    }

    @Subcommand("list")
    public void onList(CommandSender sender){

        if(plugin == null){
            sender.sendMessage("Injection of FEPlugin has failed.");
            LogUtils.warning("Injection of FEPlugin has failed.");
            return;
        }

        int size = plugin.getChildModules().size();
        StringBuilder moduleNames = new StringBuilder("&a" + plugin.getModuleName());
        for (Module module : plugin.getChildModules()) {
            moduleNames.append("&f, ").append(module.isLoaded() ? "&a" + module.getModuleName() : "&c" + module.getModuleName());
        }
        sender.sendMessage(Locale.toColors("Modules (" + (size + 1) + "): &a" + moduleNames));

    }

    @Subcommand("info")
    @Syntax("<moduleName>")
    @Description("Lists all installed modules")
    public void onInfo(CommandSender sender, String moduleName){
        if (ModuleAPI.isLoaded(moduleName)) {
            ModuleUtils.sendModuleInfo(ModuleAPI.getModule(moduleName.toLowerCase()), sender);
        } else {
            Locale.message(sender, Locale.GENERAL_MODULE_NOT_FOUND);
        }
    }

    @Subcommand("disable")
    @Syntax("<moduleName>")
    @Description("Disables a module with that name if loaded")
    public void onDisable(CommandSender sender, String moduleName){
        if (ModuleAPI.getModule(moduleName) != null) {
            if (ModuleAPI.disableModule(moduleName)) {
                Locale.success(sender, "&aModule &c'" + moduleName + "' &ahas been disabled.");
            } else {
                Locale.error(sender, "&cThat module is currently not loaded.");
            }
        } else {
            Locale.message(sender, Locale.GENERAL_MODULE_NOT_FOUND);
        }
    }

    @Subcommand("enable")
    @Syntax("<moduleName>")
    @Description("Enables a module with that name if loaded")
    public void onEnable(CommandSender sender, String moduleName){
        if (ModuleAPI.getModule(moduleName) != null) {
            if (ModuleAPI.enableModule(moduleName) != null) {
                Locale.success(sender, "&aModule '" + moduleName + "' has been enabled.");
            } else {
                Locale.error(sender, "&cThat module is already loaded.");
            }
        } else {
            //System.out.println("Module Name is: " + moduleName);
            Locale.message(sender, Locale.GENERAL_MODULE_NOT_FOUND);
        }
    }
}
