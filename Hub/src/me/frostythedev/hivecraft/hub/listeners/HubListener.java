package me.frostythedev.hivecraft.hub.listeners;

import me.frostythedev.hivecraft.hub.Constants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class HubListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
       if(!event.getPlayer().hasPermission(Constants.PERM_OWNER)){
           event.setCancelled(true);
       }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event){
        if(!event.getPlayer().hasPermission(Constants.PERM_OWNER)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

    }
}
