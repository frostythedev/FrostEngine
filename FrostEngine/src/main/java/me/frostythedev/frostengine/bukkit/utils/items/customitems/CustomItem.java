package me.frostythedev.frostengine.bukkit.utils.items.customitems;

import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Turqmelon on 11/10/16.
 */
public abstract class CustomItem {


    private String name;
    private List<Action> registeredActions = new ArrayList<>();
    private String targetNBT = null;
    private boolean useCooldown = false;

    public CustomItem(String name) {
        this.name = name;
    }

    public CustomItem(String name, List<Action> registeredActions, String targetNBT) {
        this.name = name;
        this.registeredActions = registeredActions;
        this.targetNBT = targetNBT;
    }

    public CustomItem(String name, List<Action> registeredActions, String targetNBT, boolean useCooldown) {
        this.name = name;
        this.registeredActions = registeredActions;
        this.targetNBT = targetNBT;
        this.useCooldown = useCooldown;
    }

    protected void takeAndSetHand(Player player, ItemStack item) {
        int hand = item.getAmount() - 1;
        if (hand > 0) {
            item.setAmount(hand);
            player.setItemInHand(item);
        } else {
            player.setItemInHand(null);
        }
        player.updateInventory();
    }

    // Return TRUE to cancel the interact event
    public boolean handleInteraction(Player player, ItemStack hand, NBTBase nbt, PlayerInteractEvent ie) {
        return false;
    }

    public List<Action> getRegisteredActions() {
        return registeredActions;
    }

    public String getTargetNBT() {
        return targetNBT;
    }

    public boolean isUseCooldown() {
        return useCooldown;
    }

    public String getName() {
        return name;
    }

    public abstract ItemStack getItemStack(List<String> args) throws CustomItemException;

    public List<String> getTabCompletionItems(List<String> args) {
        return new ArrayList<>();
    }
}
