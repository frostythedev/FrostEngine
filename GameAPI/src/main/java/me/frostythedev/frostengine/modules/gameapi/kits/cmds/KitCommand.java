package me.frostythedev.frostengine.modules.gameapi.kits.cmds;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import com.google.inject.Inject;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.modules.gameapi.GameAPI;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

@CommandAlias("kits|fekits|fekit")
public class KitCommand extends BaseCommand {

    @Inject
    private GameAPI plugin;

    private Game game;

    public KitCommand(Game game) {
        this.game = game;
    }

    @Subcommand("create")
    @CommandPermission("")
    public void onCreate(Player player, String[] args){

        if (args.length <= 1) {
            Locale.error(player, "&cYou need to specify a name and permission for this kit.");
        }

        String kitName = args[1];
        String perm = args[2];
        if (game.getKitManager().getKit(kitName) !=  null){
            Locale.error(player, "&cA kit with this name already exists.");
            return;
        }

        if(player.hasMetadata("kit-creation")){
            Locale.error(player, "&cYou are still in the process of creating a kit.");
            return;
        }

        player.setMetadata("kit-creation", new FixedMetadataValue(plugin, (kitName + ";" + perm)));
        Locale.success(player, "&aRight click a chest to create kit '" + kitName + "' with that content.");

    }
}
