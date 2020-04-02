package me.frosthedev.frostengine.modules;

import me.frostythedev.frostengine.bukkit.menu.MenuItem;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.material.MaterialData;

public class HatItem extends MenuItem {

    private final MaterialData UNLOCKED = new MaterialData(Material.INK_SACK, (byte) 10);
    private final MaterialData LOCKED = new MaterialData(Material.INK_SACK, (byte) 8);

    private Player player;
    private HatGadget hat;

    public HatItem(Player player, HatGadget hat) {
        this.player = player;
        this.hat = hat;

        if (isUnlocked()) {
            setIcon(UNLOCKED);
            setText("&6" + hat.getName());
            setDescriptions("&7Click to equip!");
        } else {
            setIcon(LOCKED);
            setText("&6Locked");
            setDescriptions("&7Unlocked this by visiting out store:", "&b&n%store%");
        }
    }

    public boolean isUnlocked() {
        return hat.hasCompleted(player);
    }

    @Override
    public void onClick(Player player, ClickType type) {
        if(getIcon().equals(UNLOCKED)){
            if (hat.hasCompleted(player)) {
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                hat.activate(player);
            } else {
                Locale.error(player, "&cYou do not have access to this hat.");
            }
        }
    }
}
