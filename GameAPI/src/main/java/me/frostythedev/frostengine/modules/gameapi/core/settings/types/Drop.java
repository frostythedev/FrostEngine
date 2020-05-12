package me.frostythedev.frostengine.modules.gameapi.core.settings.types;

import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.bukkit.utils.items.ItemBuilder;
import me.frostythedev.frostengine.modules.gameapi.core.Setting;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;

public class Drop extends Setting<PlayerDropItemEvent> {

    public Drop(Game minigame, String name, boolean enabled) {
        super(minigame, name, enabled);
        setIcon(new ItemBuilder(Material.ANVIL).build());
    }

    @Override @EventHandler
    public void onAction(PlayerDropItemEvent event) {
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
