package me.frostythedev.frostengine.modules.gameapi.core.settings.types;

import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.modules.gameapi.Minigame;
import me.frostythedev.frostengine.modules.gameapi.ModuleGameAPI;
import me.frostythedev.frostengine.modules.gameapi.core.Setting;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Teleport extends Setting<PlayerTeleportEvent> {

    public Teleport(Minigame minigame, String name, boolean enabled) {
        super(minigame, name, enabled);
    }

    @Override
    public void onAction(PlayerTeleportEvent event) {
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
