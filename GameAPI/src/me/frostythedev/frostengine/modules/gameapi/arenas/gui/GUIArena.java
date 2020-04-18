package me.frostythedev.frostengine.modules.gameapi.arenas.gui;

import me.frostythedev.frostengine.modules.gameapi.Minigame;
import me.frostythedev.frostengine.modules.gameapi.arenas.GameArena;
import me.frostythedev.frostengine.modules.gameapi.core.gui.GUIMinigame;
import me.frostythedev.frostengine.bukkit.utils.gui.GUI;
import me.frostythedev.frostengine.bukkit.utils.items.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class GUIArena extends GUI {

    private Minigame minigame;
    private int arenaAmount;

    private int page = 1;
    private boolean nextPage;
    private boolean prevPage;
    private int size;

    private final int amountPerLine = 9;


    private Map<Integer, String> arenaIndexes = new HashMap<>();

    public GUIArena(Minigame minigame) {
        super(ChatColor.BOLD + minigame.getName() + "'s Arenas",
                ((int)(Math.ceil((minigame.getArenaManager().getSize()/9.0))+1.0)*9)
        );

        setUpdateTicks(20);

        this.minigame = minigame;

        this.arenaAmount = minigame.getArenaManager().getSize();
        if(this.arenaAmount > 45){
            nextPage = true;
            this.getProxyInventory().setItem(6, NEXT_PAGE);
        }
        this.size = (int)(Math.ceil((minigame.getArenaManager().getSize()/9.0))+1.0)*9;
    }

    @Override
    protected void onGUIInventoryClick(InventoryClickEvent event) {
        int raw = event.getRawSlot();

        if(raw == 2 && isPrevPage()){
            this.page = getPage()-1;
            repopulate();
            player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
        }else if(raw == 6 && isNextPage()){
            this.page = getPage()+1;
            repopulate();
            player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
        }else if (raw == 3 && !isPrevPage()){
            new GUIMinigame(this.minigame).open((Player) event.getWhoClicked());
        }
    }

    @Override
    protected void populate() {
        Inventory inv = getProxyInventory();

        //Bukkit.getLogger().info("Inv size for arenas is " + this.size);

        prevPage = getPage() > 1;
        if(prevPage){
           inv.setItem(3, PREVIOUS_PAGE);
        }else{
            inv.setItem(3, new ItemBuilder(Material.ARROW)
            .withCustomName("&a&l< &aPrevious Page")
            .withLore("&7To Minigame Menu").build());
        }

        inv.setItem(4, new ItemBuilder(Material.NETHER_STAR)
        .withCustomName("&aArena Menu")
        .withLore("&7Left Click to edit "
                ,"&7an arenas settings")
        .build());

        int slot = 9;
        int startIndex = amountPerLine * (getPage()-1);
        int endIndex = amountPerLine * getPage();

       // Bukkit.getLogger().info("Loaded arenas: " + arenaAmount);

        nextPage = arenaAmount > endIndex;
        if(isNextPage()){
            inv.setItem(5, NEXT_PAGE);
        }

        for (int i = startIndex; i < endIndex; i++) {
            if(i+1 > minigame.getArenaManager().getSize()) break;
            GameArena arena = minigame.getArenaManager().getArena(minigame.getArenaManager().getArenaNames().get(i));

            if(arena != null){

                inv.setItem(slot, arena.getIcon());
                arenaIndexes.put(slot, arena.getArenaName());
             //   Bukkit.getLogger().info("Added " + arena.getArenaName() + " icon to gui in slot " + slot);

            }
            slot++;
        }
    }


    public int getPage() {
        return page;
    }

    public boolean isNextPage() {
        return nextPage;
    }

    public boolean isPrevPage() {
        return prevPage;
    }
}
