package me.frostythedev.frostengine.modules.ranks.listeners;

import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.event.api.AbstractListener;
import me.frostythedev.frostengine.modules.ranks.ModuleRanks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener extends AbstractListener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        FEPlugin.get().getThreadManager().runTaskOneTickLater(() -> {
            if(!ModuleRanks.get().getPlayerManager().isLoaded(event.getPlayer())){
                System.out.println("Player is not loaded, loading now...");
                ModuleRanks.get().getPlayerManager().loadPlayer(event.getPlayer());
                System.out.println("Player is NOW loaded.");
            }
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        FEPlugin.get().getThreadManager().runTaskOneTickLater(() -> {
            if(ModuleRanks.get().getPlayerManager().isLoaded(event.getPlayer())){
                ModuleRanks.get().getPlayerManager().savePlayer(
                        ModuleRanks.get().getPlayerManager().getPlayer(event.getPlayer()));
            }
        });
    }

}
