package me.frostythedev.frostengine.modules.gameapi.core.settings.types;

import me.frostythedev.frostengine.modules.gameapi.Minigame;
import me.frostythedev.frostengine.modules.gameapi.core.Setting;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

public class Explode extends Setting<EntityExplodeEvent> {

    public Explode(Minigame minigame, String name, boolean enabled) {
        super(minigame, name, enabled);
        setIcon(new ItemStack(Material.TNT));
    }

    @Override @EventHandler
    public void onAction(EntityExplodeEvent event) {
        if(!isEnabled()){
            event.setCancelled(true);
        }
    }
}
