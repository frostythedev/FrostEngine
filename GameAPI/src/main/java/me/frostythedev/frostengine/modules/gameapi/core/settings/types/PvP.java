package me.frostythedev.frostengine.modules.gameapi.core.settings.types;

import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.modules.gameapi.Minigame;
import me.frostythedev.frostengine.modules.gameapi.ModuleGameAPI;
import me.frostythedev.frostengine.modules.gameapi.core.Setting;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class PvP extends Setting<EntityDamageByEntityEvent> {

    public PvP(Game minigame, String name, boolean enabled) {
        super(minigame, name, enabled);
        setIcon(new ItemStack(Material.DIAMOND_SWORD));
    }

    @Override @EventHandler
    public void onAction(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            if (event.getEntity() instanceof Player) {
                if (!isEnabled()) {
                    event.setCancelled(true);

                    getMinigame().getSettingManager().get("Notify").ifPresent(
                            s -> {
                                if(s.isEnabled()){
                                    Locale.messagef(event.getEntity(), "&4&l>> &cYou are not allowed to %s!", getName().toLowerCase());
                                }
                            }
                    );
                }
            }
        }
    }
}
