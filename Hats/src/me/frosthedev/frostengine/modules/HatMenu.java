package me.frosthedev.frostengine.modules;

import me.frostythedev.frostengine.bukkit.menu.ItemMenu;
import org.bukkit.entity.Player;

public class HatMenu extends ItemMenu {

    public HatMenu(Player player) {
        super("Hats", 6);

        int count = 0;
        for(HatGadget hat : ModuleHats.get().getHatManager().getHats()){
            HatItem item = new HatItem(player, hat);
            addMenuItem(item, count);
            count++;
        }
    }
}
