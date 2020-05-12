package me.frostythedev.frostengine.modules.gameapi.core.settings.types;

import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.modules.gameapi.core.Setting;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Teleport extends Setting<PlayerTeleportEvent> {

    public Teleport(Game minigame, String name, boolean enabled) {
        super(minigame, name, enabled);
    }

    @Override @EventHandler
    public void onAction(PlayerTeleportEvent event) {

        switch (event.getCause()){
            case END_PORTAL:
            case NETHER_PORTAL:
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
                break;
            default:
                break;
        }
    }
}
