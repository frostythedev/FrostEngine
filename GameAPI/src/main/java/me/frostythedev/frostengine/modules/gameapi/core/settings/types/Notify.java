package me.frostythedev.frostengine.modules.gameapi.core.settings.types;

import me.frostythedev.frostengine.bukkit.utils.items.ItemBuilder;
import me.frostythedev.frostengine.modules.gameapi.Minigame;
import me.frostythedev.frostengine.modules.gameapi.core.Setting;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import org.bukkit.Material;

public class Notify extends Setting {

    public Notify(Game minigame, String name, boolean enabled) {
        super(minigame, name, enabled);
        setIcon(new ItemBuilder(Material.BLAZE_POWDER).build());
    }

    @Override
    public void onAction(Object event) {

    }
}
