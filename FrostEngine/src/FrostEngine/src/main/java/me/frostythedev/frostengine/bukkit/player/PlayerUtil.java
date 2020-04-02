package me.frostythedev.frostengine.bukkit.player;

import me.frostythedev.frostengine.bukkit.utils.items.ArmorSlot;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class PlayerUtil {

    public static void setArmor(Map<ArmorSlot, ItemStack> armor, Player player){
        Validate.notNull(armor, "Armor map cannot be null");
        if(!armor.isEmpty()){
            for(Map.Entry<ArmorSlot, ItemStack> entry : armor.entrySet()){
                switch (entry.getKey()){
                    case HELMET:
                        player.getInventory().setHelmet(entry.getValue());
                        break;
                    case CHEST_PLATE:
                        player.getInventory().setChestplate(entry.getValue());
                        break;
                    case LEGGINGS:
                        player.getInventory().setLeggings(entry.getValue());
                        break;
                    case BOOTS:
                        player.getInventory().setBoots(entry.getValue());
                        break;
                }
            }
        }
    }

    public static boolean handIsEmpty(Player player) {
        return player.getItemInHand() == null;
    }
}
