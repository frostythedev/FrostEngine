package me.frostythedev.hivecraft.hub.commands;

import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.cmd.core.SubCommand;
import me.frostythedev.frostengine.legacy.ui.SendGame;
import me.frostythedev.hivecraft.hub.HubPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCmd extends SubCommand {

    public SetSpawnCmd() {
        super("setspawn", "hivecraft.admin");
        setPlayerOnly(true);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        HubPlugin.get().getConfig().set("spawn-location", FEPlugin.getGson().toJson(player.getLocation()));
        HubPlugin.get().saveConfig();
        SendGame.toPlayer("&e>> You have set the spawn location to your feet.", player);
    }
}
