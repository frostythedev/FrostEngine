package me.frostythedev.frostengine.bukkit.utils.items.customitems;

/**
 * Created by Turqmelon on 11/10/16.
 */
public class CustomItemException extends Exception {

    private CustomItem item;
    private String message;

    public CustomItemException(CustomItem item, String message) {
        this.item = item;
        this.message = message;
    }

    public CustomItemException(String message) {
        this.message = message;
    }

    public CustomItem getItem() {
        return item;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
