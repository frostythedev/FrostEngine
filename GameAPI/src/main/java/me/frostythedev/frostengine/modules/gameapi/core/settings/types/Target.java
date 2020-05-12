package me.frostythedev.frostengine.modules.gameapi.core.settings.types;

import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.modules.gameapi.core.Setting;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;

public class Target extends Setting<EntityTargetEvent> {

    public Target(Game minigame, String name, boolean enabled) {
        super(minigame, name, enabled);
        setIcon(new ItemStack(Material.BOW));
    }

    @Override @EventHandler
    public void onAction(EntityTargetEvent event) {
        if(!isEnabled()){
            event.setCancelled(true);

            getMinigame().getSettingManager().get("Notify").ifPresent(
                    s -> {
                        if(s.isEnabled()){
                            Locale.messagef(event.getTarget(), "&4&l>> &cYou are not allowed to %s!", getName().toLowerCase());
                        }
                    }
            );
        }
    }
}
