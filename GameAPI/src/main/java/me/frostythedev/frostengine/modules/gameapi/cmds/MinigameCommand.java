package me.frostythedev.frostengine.modules.gameapi.cmds;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.google.inject.Inject;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.modules.gameapi.Minigame;
import me.frostythedev.frostengine.modules.gameapi.ModuleGameAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("minigame|mg")
@CommandPermission("frostengine.gameapi.admin")
public class MinigameCommand extends BaseCommand {

    @Inject
    ModuleGameAPI gameAPI;

    @Default
    @HelpCommand
    public void onHelp(CommandSender sender) {

    }

    @Subcommand("settings|options")
    public void openSettingsMenu(Player player, String minigameName){

        if(gameAPI.getMinigameManager().getAll().size() > 0){

            if (!gameAPI.getMinigameManager().getMinigame(minigameName).isPresent()) {
                Locale.error(player, "&cThere is no minigame with that name loaded!");
                return;
            }
            Minigame mg = gameAPI.getMinigameManager().getMinigame(minigameName).get();
            mg.getSettings().getSettingsGUI().open(player);

        }else{

            Locale.error(player, "There are no minigames loaded");
        }
    }
}
