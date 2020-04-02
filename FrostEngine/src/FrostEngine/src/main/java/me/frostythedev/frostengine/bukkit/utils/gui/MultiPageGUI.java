package me.frostythedev.frostengine.bukkit.utils.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class MultiPageGUI extends GUI{

    private int page = 1;
    private boolean nextPage;
    private boolean prevPage;

    private final int amountPerLine = 9;

    public static final int[] USABLE_INDEXES = {
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34,
            37, 38, 39, 40, 41, 42, 43};

    private Map<Integer, String> indexes = new HashMap<>();

    public MultiPageGUI(String title, int size) {
        super(title, size);
    }

    public MultiPageGUI(String title, InventoryType type) {
        super(title, type);
    }

    public MultiPageGUI(Inventory bukkitInventory) {
        super(bukkitInventory);
    }

    @Override
    protected void onGUIInventoryClick(InventoryClickEvent event) {

    }

    @Override
    protected void populate() {

    }
}
