package me.frostythedev.frostengine.modules.gameapi.cmds;

import me.frostythedev.frostengine.bukkit.cmd.api.Command;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.modules.gameapi.ModuleGameAPI;
import me.frostythedev.frostengine.modules.gameapi.core.gui.GUIMinigame;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDMinigame extends Command {

    public CMDMinigame() {
        super("minigame", "frostengine.admin.minigame");
        setPlayerOnly(true);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length >= 1) {
            String sub = args[0];
            String minigameName = args[1];

            if (sub.equalsIgnoreCase("open")) {


                if(ModuleGameAPI.get().getMinigameManager().getAll().size() > 0){
                    new GUIMinigame(ModuleGameAPI.get().getMinigameManager()
                            .getAll().get(0)).open(player);
                }else{
                    Locale.error(player, "There are no minigames loaded");

                }

                /*if (ModuleGameAPI.get().getMinigameManager().getMinigame(minigameName)
                        .isPresent()) {
                    new GUIMinigame(ModuleGameAPI.get().getMinigameManager()
                            .getMinigame(minigameName).get()).open(player);
                } else {
                    Locale.error(player, "A minigame could not be found with that name: " + minigameName);
                }*/


            } else if (sub.equalsIgnoreCase("settings")) {

                if(ModuleGameAPI.get().getMinigameManager().getAll().size() > 0){
                   ModuleGameAPI.get().getMinigameManager()
                            .getAll().get(0).getSettings().getSettingsGUI().open(player);
                }else{
                    Locale.error(player, "There are no minigames loaded");

                }

                /*if (ModuleGameAPI.get().getMinigameManager().getMinigame(minigameName)
                        .isPresent()) {

                    ModuleGameAPI.get().getMinigameManager()
                            .getMinigame(minigameName).get().getSettings()
                            .getSettingsGUI().open(player);

                } else {
                    Locale.error(player, "A minigame could not be found with that name: " + minigameName);
                }*/
            }
        }
    }
}
