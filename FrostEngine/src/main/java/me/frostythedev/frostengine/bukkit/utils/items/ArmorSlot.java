package me.frostythedev.frostengine.bukkit.utils.items;

public enum ArmorSlot {
    HELMET(103),
    CHEST_PLATE(104),
    LEGGINGS(105),
    BOOTS(106);

    private int inventorySlot;

    ArmorSlot(int inventorySlot) {
        this.inventorySlot = inventorySlot;
    }

    public int getInventorySlot() {
        return inventorySlot;
    }


    @Override
    public String toString() {
        return name();
    }
}
