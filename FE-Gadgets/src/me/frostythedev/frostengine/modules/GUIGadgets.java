package me.frostythedev.frostengine.modules;

import me.frostythedev.frostengine.bukkit.utils.gui.GUI;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.bukkit.utils.items.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class GUIGadgets extends GUI {

    private Player player;

    public GUIGadgets(Player player) {
        super("GADGETS - Your Arsenal", 54);
        this.addedGadgets = new HashMap<>();
        this.player = player;
    }

    private final int[] OPEN_SLOTS = new int[]{
            18, 19, 20, 21, 22, 23,
            24, 25, 26, 27, 28, 29,
            30, 31, 32, 33, 34, 35};

    private Map<Integer, Integer> addedGadgets;

    @Override
    protected void onGUIInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null) {
            if (addedGadgets.containsKey(event.getSlot())) {
                ColleactableGadget gadget = ModuleCollectable.get().getManager().getGadget(addedGadgets.get(event.getSlot()));

                if (gadget.hasCompleted((Player) event.getWhoClicked())) {
                    gadget.activate((Player) event.getWhoClicked());
                } else {
                    this.close();
                    Locale.error(event.getWhoClicked(), "You do not have access to this gadget as yet.");
                }
            }
        }
    }

    @Override
    protected void populate() {
        Inventory inv = this.getProxyInventory();

        ItemStack filler = new ItemBuilder(Material.STAINED_GLASS_PANE)
                .withData((byte) 15)
                .withCustomName(" ").build();

        for (int i = 9; i < 18; i++) {
            inv.setItem(i, filler);
        }
        for (int i = 36; i < 45; i++) {
            inv.setItem(i, filler);
        }

        for (int i : OPEN_SLOTS) {
            if (inv.getItem(i) == null) {

                for (ColleactableGadget gadget : ModuleCollectable.get().getManager().getAllGadgets()) {
                    if (!addedGadgets.values().contains(gadget.getId())) {
                        if (this.player != null) {

                            if (gadget.hasCompleted(this.player)) {

                                inv.setItem(i, (gadget.getIcon() != null ? gadget.getIcon().build() :
                                        new ItemBuilder(Material.INK_SACK)
                                                .withData(DyeColor.GREEN.getData())
                                                .withCustomName(gadget.getName())
                                                .withLore("&a&lUNLOCKED", "" +
                                                        "&7Click to activate!")
                                                .build()
                                ));

                            } else {
                                inv.setItem(i, new ItemBuilder(Material.STAINED_CLAY)
                                        .withCustomName("&c&lLOCKED")
                                        .withLore("&7You have not unlocked this gadget yet!",
                                                "&7Gather all its parts then try again.",
                                                " ",
                                                "&a" + gadget.getPercentageCompleted(getPlayer()) + "/100")
                                        .withData((byte) 9)
                                        .build());
                            }
                        }

                        addedGadgets.put(i, gadget.getId());
                    }
                }
            }
        }


    }
}
