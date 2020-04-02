package me.frostythedev.frostengine.modules;

import me.frostythedev.frostengine.bukkit.thread.Tasks;
import me.frostythedev.frostengine.bukkit.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GadgetExample extends ColleactableGadget {

    public GadgetExample() {
        super(1, "MagicHoe", true);
        this.setParts("Hoe", "Magic", "Cow");

        setIcon(new ItemBuilder(Material.GOLD_HOE)
                .withCustomName("&6Magic Hoe of Tevin")
                .withLore("&7Majestic Hoe",
                        "&7That guards all of Cows",
                        "&7Everywhere and always"));
    }

    @Override
    public void activate(Player player) {

        if (!CollectableAPI.isAllowed(player, this)) {
            player.sendMessage("You have not finished collecting this gadget.");
        } else {
            ItemStack hoe = getIcon().build();

            player.sendMessage("You have been granted the magic hoe for 10 seconds.");
            player.getInventory().addItem(hoe);
            player.getWorld().dropItemNaturally(player.getLocation().clone().add(0, 2, 0), hoe);

            Tasks.runLater(() -> {
                player.getInventory().remove(hoe);
                player.sendMessage("Your time has expired for the Magic Hoe.");
            }, 20 * 10);
        }
    }
}
