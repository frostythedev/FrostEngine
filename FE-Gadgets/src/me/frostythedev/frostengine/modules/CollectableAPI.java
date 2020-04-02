package me.frostythedev.frostengine.modules;

import me.frostythedev.frostengine.config.BukkitDocument;
import org.bukkit.entity.Player;

import java.util.List;

public class CollectableAPI {

    public static boolean isAllowed(Player player, ColleactableGadget gadget) {
        return hasCompleted(player, gadget);
    }

    public static boolean hasCompleted(Player player, ColleactableGadget gadget) {
        BukkitDocument document = BukkitDocument.of(ModuleCollectable.get().getModuleDirectory() + "/players/"
                + player.getUniqueId().toString() + ".yml");
        document.load();
        document.createStringListIfNotExists("Unlocked");
        List<String> stringList = document.getStringList("Unlocked");

        String[] savedParts = CollectableUtils.searchStringListForCollectable(stringList, gadget);
        return gadget.hasCompleted(savedParts);
    }

    public static int getPercentageCompleted(Player player, ColleactableGadget gadget) {
        BukkitDocument document = BukkitDocument.of(ModuleCollectable.get().getModuleDirectory() + "/players/"
                + player.getUniqueId().toString() + ".yml");
        document.load();
        document.createStringListIfNotExists("Unlocked");
        List<String> stringList = document.getStringList("Unlocked");

        String[] savedParts = CollectableUtils.searchStringListForCollectable(stringList, gadget);
        return gadget.getPercentageCompleted(savedParts);
    }

    public static int getAmountCompleted(Player player, ColleactableGadget gadget) {
        BukkitDocument document = BukkitDocument.of(ModuleCollectable.get().getModuleDirectory() + "/players/"
                + player.getUniqueId().toString() + ".yml");
        document.load();
        document.createStringListIfNotExists("Unlocked");
        List<String> stringList = document.getStringList("Unlocked");

        String[] savedParts = CollectableUtils.searchStringListForCollectable(stringList, gadget);
        return gadget.getAmountCompleted(savedParts);
    }

    public static boolean giveCollectable(Player player, ColleactableGadget gadget) {
        return gadget != null && gadget.give(player);
    }

    public static boolean giveCollectable(Player player, String name) {
        return giveCollectable(player, ModuleCollectable.get().getManager().getGadget(name));
    }
}
