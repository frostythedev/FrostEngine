package me.frostythedev.frostengine.modules.gameapi.core;

import me.frostythedev.frostengine.bukkit.utils.items.ItemBuilder;
import me.frostythedev.frostengine.modules.gameapi.Minigame;
import me.frostythedev.frostengine.modules.gameapi.core.settings.PlayerSetting;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class Setting<T> implements PlayerSetting<T>, Listener {

    private String name;
    private boolean enabled;
    private ItemStack icon;
    private String description;

    private Minigame minigame;

    public Setting(Minigame minigame, String name, boolean enabled) {
        this.minigame = minigame;
        this.name = name;
        this.enabled = enabled;
        this.description = "&7Default Description =(";
        setIcon(new ItemBuilder(Material.INK_SACK).withData((byte) 10).build());
    }

    public ItemStack getCompleteIcon(){
        return new ItemBuilder(getIcon())
                .withCustomName((isEnabled() ? "&a" + getName() : "&c" + getName()))
                .withLore(description)
                .build()
                ;
    }

    public ItemStack getCompleteIconOpposite(){
        return new ItemBuilder(getIcon())
                .withCustomName((isEnabled() ? "&c" + getName() : "&a" + getName()))
                .withLore(description)
                .build()
                ;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Minigame getMinigame() {
        return this.minigame;
    }

    public void setMinigame(Minigame minigame) {
        this.minigame = minigame;
    }
}
