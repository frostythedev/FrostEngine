package me.frostythedev.versus;

import co.aikar.commands.BukkitCommandIssuer;
import co.aikar.commands.ConditionFailedException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.legacy.module.Plugin;
import me.frostythedev.versus.cmds.VersusCommand;
import org.bukkit.entity.Player;

@Singleton
public class VersusPlugin extends Plugin {

    @Inject FEPlugin fePlugin;

   /* public VersusPlugin() {
        super("1v1s");
        setParentName("GameAPI");
    }*/

    @Override
    public void onModuleEnable() {
        super.onModuleEnable();

        fePlugin.getInjector().injectMembers(this);

        fePlugin.getCommandManager().getCommandConditions().addCondition("duelTarget", (context) -> {
            BukkitCommandIssuer issuer = context.getIssuer();
            if (!issuer.isPlayer()) {
                throw new ConditionFailedException("Target must be a player");
            }
            Player player = (Player) issuer;
            if(!player.isOnline()) {
                throw new ConditionFailedException("Player must be online");
            }
        });

        fePlugin.getCommandManager().registerCommand(new VersusCommand());

    }

    @Override
    public void onModuleDisable() {
        super.onModuleDisable();
    }
}
