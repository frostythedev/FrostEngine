/*
package me.frostythedev.frostengine.modules.gameapi.core.gui;

import me.frostythedev.frostengine.modules.gameapi.arenas.gui.GUIArena;
import me.frostythedev.frostengine.legacy.gui.GUI;
import me.frostythedev.frostengine.bukkit.utils.items.ItemBuilder;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class GUIMinigame extends GUI{

    private Game minigame;

    public GUIMinigame(Game minigame) {
        super(minigame.getName() + "'s Minigame Settings", 36);

        this.minigame = minigame;
        setUpdateTicks(20);
    }

    @Override
    protected void onGUIInventoryClick(InventoryClickEvent event) {
        int raw = event.getRawSlot();

        if(raw == 11){
            new GUIArena(this.minigame).open((Player) event.getWhoClicked());
        }
    }

    @Override
    protected void populate() {
        Inventory inv = getProxyInventory();

        ItemStack viewTeams = new ItemBuilder(Material.WOOL)
                .withCustomName("&fView Teams")
                .withData((byte) ThreadLocalRandom.current().nextInt(15))
                .build();
        ItemStack viewKits = new ItemBuilder(Material.DIAMOND_SWORD)
                .withCustomName("&aView Kits")
                .build();
        ItemStack viewArenas = new ItemBuilder(Material.GLASS)
                .withCustomName("&bView Arenas")
                .build();

        inv.setItem(9, viewTeams);
        inv.setItem(10, viewKits);
        inv.setItem(11, viewArenas);

        for(int i = 12; i < 36; i++){
            inv.setItem(i,new ItemBuilder(Material.STAINED_GLASS_PANE)
                    .withData((byte) 15)
                    .withCustomName("&c&lComing Soon")
                    .build());
        }
    }
}
*/
