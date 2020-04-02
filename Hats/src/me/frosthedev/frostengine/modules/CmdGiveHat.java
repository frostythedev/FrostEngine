package me.frosthedev.frostengine.modules;

import me.frostythedev.frostengine.bukkit.cmd.Command;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdGiveHat extends Command {

    private ModuleHats module = ModuleHats.get();

    public CmdGiveHat() {
        super("givehat");
        this.setUsage("<player> <hat>");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (args.length == 2) {
            HatGadget hat = module.getHatManager().getHat(args[1].replace("_", " "));
            if (hat == null) {
                Locale.error(sender, "&cCould not find a valid hat with that name '" + args[1] + "'!");
                return;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                Locale.error(sender, "&cCould not find a target with that name '" + args[1] + "'!");
                return;
            }

            if (hat.give(target)) {
                Locale.success(sender, "&aYou have given 1x [" + hat.getName() + "] to &7" + target.getName());
            } else {
                Locale.error(sender, "&cThis player already owns this hat.");
            }
        } else {
            this.sendUsage(sender);
        }
    }
}
