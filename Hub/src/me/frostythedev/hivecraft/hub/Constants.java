package me.frostythedev.hivecraft.hub;

import me.frostythedev.frostengine.bukkit.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface Constants {

    ItemStack SERVER_SELECTOR = ItemBuilder.of(Material.GOLD_INGOT)
            .name("&eQuick Connect &7(Right Click)").item();

    String PERM_OWNER = "hivecraft.owner";
}
