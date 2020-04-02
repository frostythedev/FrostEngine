package me.frostythedev.frostengine.bukkit.gameapi.cmds;

import me.frostythedev.example.game.ModuleExample;
import me.frostythedev.frostengine.bukkit.cmd.api.Command;
import me.frostythedev.frostengine.bukkit.gameapi.core.GUIMinigame;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDMinigame extends Command{

    public CMDMinigame() {
        super("minigame", "frostengine.admin.minigame");
        setPlayerOnly(true);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
       Player player = (Player) sender;
        new GUIMinigame(ModuleExample.get().getGame()).open(player);
    }
}
