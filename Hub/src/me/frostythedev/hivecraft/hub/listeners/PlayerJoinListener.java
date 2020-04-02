package me.frostythedev.hivecraft.hub.listeners;

import me.frostythedev.hivecraft.hub.Config;
import me.frostythedev.hivecraft.hub.Constants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        if(Config.SPAWN_LOCATION != null){
            player.teleport(Config.SPAWN_LOCATION);
        }

        player.getInventory().setItem(0, Constants.SERVER_SELECTOR);
    }
}
