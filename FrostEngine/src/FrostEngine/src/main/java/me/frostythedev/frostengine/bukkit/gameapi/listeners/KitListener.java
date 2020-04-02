package me.frostythedev.frostengine.bukkit.gameapi.listeners;

import me.frostythedev.frostengine.bukkit.gameapi.Minigame;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.gameapi.kits.GameKit;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class KitListener implements Listener {

    private Minigame minigame;

    public KitListener(Minigame minigame) {
        this.minigame = minigame;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getClickedBlock() != null && event.getClickedBlock().getType().equals(Material.CHEST)) {
                if (event.getPlayer().hasMetadata("kit-creation")) {
                    String kitName = event.getPlayer().getMetadata("kit-creation").get(0).asString().split(";")[0];
                    String perm = event.getPlayer().getMetadata("kit-creation").get(0).asString().split(";")[1];

                    GameKit newKit = new GameKit(-1, kitName, perm);
                    if (newKit.getKitFromChest(event.getPlayer(), event.getClickedBlock())) {
                        if (minigame.getKitManager().createKit(newKit)) {
                            Locale.success(event.getPlayer(), "&aKit: " + kitName + " has been created and saved to mysql.");
                            event.getPlayer().removeMetadata("kit-creation", FEPlugin.get());
                        }
                    }
                }
            }
        }
    }
}
