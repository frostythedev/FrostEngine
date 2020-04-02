package me.frostythedev.frostengine.bukkit.cmd.types;

import me.frostythedev.frostengine.bukkit.module.ModuleUtils;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.cmd.api.Command;
import me.frostythedev.frostengine.bukkit.cmd.api.SubCommand;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.bukkit.module.Module;
import me.frostythedev.frostengine.bukkit.module.ModuleAPI;
import org.bukkit.command.CommandSender;

/**
 * Programmed by Tevin on 8/6/2016.
 */
public class CmdModule extends Command {

    public CmdModule() {
        super("module", "frostengine.admin");
        setPlayerOnly(false);
        setUsage("list|info|disable|enable");
        this.addSubCommand(new ListCommand());
        this.addSubCommand(new InfoCommand());
        this.addSubCommand(new EnableCommand());
        this.addSubCommand(new DisableCommand());
    }

    class ListCommand extends SubCommand {

        public ListCommand() {
            super("list", "", "", "Lists all installed modules");
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            int size = FEPlugin.get().getChildModules().size();

            StringBuilder moduleNames = new StringBuilder("&a" + FEPlugin.get().getModuleName());

            for (Module module : FEPlugin.get().getChildModules()) {
                moduleNames.append("&f, ").append(module.isLoaded() ? "&a" + module.getModuleName() : "&c" + module.getModuleName());
            }

            sender.sendMessage(Locale.toColors("Modules (" + (size + 1) + "): &a" + moduleNames));
        }
    }

    class InfoCommand extends SubCommand {

        public InfoCommand() {
            super("info", "", "<module>", "Shows information about specified module");
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            if (args.length >= 2) {
                String moduleName = args[1];

                if (ModuleAPI.isLoaded(moduleName)) {
                    ModuleUtils.sendModuleInfo(ModuleAPI.getModule(moduleName.toLowerCase()), sender);
                } else {
                    sender.sendMessage(Locale.GENERAL_MODULE_NOT_FOUND);
                }
            }
        }
    }

    class DisableCommand extends SubCommand {

        public DisableCommand() {
            super("disable", "", "<module>", "Disables a module with that name if loaded");
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            if (args.length >= 2) {
                String moduleName = args[1];

                if (ModuleAPI.getModule(moduleName) != null) {
                    if (ModuleAPI.disableModule(moduleName)) {
                        Locale.success(sender, "&aModule &c'" + moduleName + "' &ahas been disabled.");
                    } else {
                        Locale.error(sender, "&cThat module is currently not loaded.");
                    }
                } else {
                    sender.sendMessage(Locale.GENERAL_MODULE_NOT_FOUND);
                }
            }
        }
    }

    class EnableCommand extends SubCommand {

        public EnableCommand() {
            super("enable", "", "<module>", "Enables a module with that name if not loaded");
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            if (args.length >= 2) {
                String moduleName = args[1];

                if (ModuleAPI.getModule(moduleName) != null) {
                    if (ModuleAPI.enableModule(moduleName) != null) {
                        Locale.success(sender, "&aModule '" + moduleName + "' has been enabled.");
                    } else {
                        Locale.error(sender, "&cThat module is already loaded.");
                    }
                } else {
                    sender.sendMessage(Locale.GENERAL_MODULE_NOT_FOUND);
                }
            }
        }
    }
}
