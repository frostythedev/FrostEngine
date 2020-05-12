package me.frostythedev.frostengine.modules.gameapi.core.settings.types;

import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.modules.gameapi.core.Setting;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class Movement extends Setting<PlayerMoveEvent> {

    public Movement(Game minigame, String name, boolean enabled) {
        super(minigame, name, enabled);
        setIcon(new ItemStack(Material.FEATHER));
    }

    @Override @EventHandler
    public void onAction(PlayerMoveEvent event) {
        if (!isEnabled()) {
            if (event.getTo().getBlockX() != event.getFrom().getBlockX() ||
                    event.getTo().getBlockZ() != event.getFrom().getBlockZ()) {
                event.setTo(event.getFrom());

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
}
