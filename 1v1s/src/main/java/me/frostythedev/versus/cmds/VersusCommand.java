package me.frostythedev.versus.cmds;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Conditions;
import co.aikar.commands.annotation.Subcommand;
import com.google.inject.Inject;
import me.frostythedev.frostengine.modules.gameapi.MinigameManager;
import me.frostythedev.versus.VersusGame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("duel")
public class VersusCommand extends BaseCommand {

    @Inject MinigameManager minigameManager;


    @Subcommand("invite")
    public void duel(Player player, @Conditions("duelTarget") String duelTarget){
        Player target = Bukkit.getPlayer(duelTarget);

        /*

         Make checks to ensure players are not in games, target is not null, an arena
         is available etc...
         */

        VersusGame versusGame = new VersusGame(player.getUniqueId(), target.getUniqueId());

        if(minigameManager.registerGame(versusGame)){
            minigameManager.addPlayerToGame(player, versusGame);
            minigameManager.addPlayerToGame(target, versusGame);

            versusGame.onMinigameEnable();
        }

    }
}
