package me.frostythedev.frostengine.bukkit.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import me.frostythedev.frostengine.bukkit.thread.Tasks;
import org.bukkit.entity.Player;

@CommandAlias("fetest|test")
@CommandPermission("frostengine.developer")
public class TestCommand {

    @Subcommand("zombonpc")
    public void onRun(Player player){
        Tasks.runOneTickLater(() -> {
            //EntityTypes.spawnEntity(new CustomZombie(player.getWorld()), player.getLocation());
        });
    }
}
