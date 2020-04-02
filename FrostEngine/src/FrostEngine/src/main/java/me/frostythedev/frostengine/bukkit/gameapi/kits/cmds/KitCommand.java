package me.frostythedev.frostengine.bukkit.gameapi.kits.cmds;

import me.frostythedev.frostengine.bukkit.gameapi.Minigame;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.cmd.api.Command;
import me.frostythedev.frostengine.bukkit.cmd.api.SubCommand;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class KitCommand extends Command {

    private Minigame minigame;

    public KitCommand(Minigame minigame) {
        super("kit", "frostengine.kit.admin");

        this.minigame = minigame;
        this.addSubCommand(new CreateCommand());
    }

    class CreateCommand extends SubCommand {

        public CreateCommand() {
            super("create", "");

            setPlayerOnly(true);
        }

        @Override
        public void run(CommandSender sender, String[] args) {
            Player player = (Player) sender;

            if (args.length <= 2) {
                Locale.error(sender, "&cYou need to specify a name and permission for this kit.");
            }

            String kitName = args[1];
            String perm = args[2];
            if (KitCommand.this.minigame.getKitManager().getKit(kitName) !=  null){
                Locale.error(sender, "&cA kit with this name already exists.");
                return;
            }

            if(player.hasMetadata("kit-creation")){
                Locale.error(player, "&cYou are still in the process of creating a kit.");
                return;
            }

            player.setMetadata("kit-creation", new FixedMetadataValue(FEPlugin.get(), (kitName + ";" + perm)));
            Locale.success(player, "&aRight click a chest to create kit '" + kitName + "' with that content.");
        }
    }
}
