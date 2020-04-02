package me.frosthedev.frostengine.modules;

import me.frostythedev.frostengine.bukkit.cmd.api.Command;
import me.frostythedev.frostengine.bukkit.cmd.api.SubCommand;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdHat extends Command {

    private ModuleHats module = ModuleHats.get();

    public CmdHat() {
        super("hats");
        setPlayerOnly(true);

        addSubCommand(new ResetCommand());
        addSubCommand(new OpenCommand());
    }

    class OpenCommand extends SubCommand {

        public OpenCommand() {
            super("gui", "");
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            Player player = (Player) sender;

           new HatMenu(player).openMenu(player);
        }
    }

    class ResetCommand extends SubCommand {

        public ResetCommand() {
            super("reset", "");
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            if(sender instanceof Player){
                Player player = (Player) sender;
                if( module.getHatManager().hasHat(player)){
                    module.getHatManager().removeHat(player);
                    Locale.success(player, "&aYou hat has been reset.");
                }else{
                    Locale.error(player, "&cYou are not wearing a hat.");
                }
            }
        }
    }
}
