package me.frostythedev.frostengine.modules.gameapi.core.settings.types;

import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.modules.gameapi.Minigame;
import me.frostythedev.frostengine.modules.gameapi.ModuleGameAPI;
import me.frostythedev.frostengine.modules.gameapi.core.Setting;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;

public class Buckets extends Setting<PlayerBucketFillEvent> {

    public Buckets(Game minigame, String name, boolean enabled) {
        super(minigame, name, enabled);
        setIcon(new ItemStack(Material.BUCKET));
    }

    @Override @EventHandler
    public void onAction(PlayerBucketFillEvent event) {
        if (!isEnabled()) {
            event.setCancelled(true);

            getMinigame().getSettingManager().get("Notify").ifPresent(
                    s -> {
                        if(s.isEnabled()){
                            Locale.messagef(event.getPlayer(), "&4&l>> &cYou are not allowed to %s!", getName().toLowerCase());
                        }
                    }
            );
        }
    }
}
