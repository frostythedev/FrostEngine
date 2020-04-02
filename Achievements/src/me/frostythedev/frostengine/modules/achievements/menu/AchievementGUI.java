package me.frostythedev.frostengine.modules.achievements.menu;

import me.frostythedev.frostengine.bukkit.utils.gui.GUI;
import me.frostythedev.frostengine.modules.achievements.ModuleAchievement;
import me.frostythedev.frostengine.modules.achievements.core.Achievement;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class AchievementGUI extends GUI {

    public AchievementGUI() {
        super("Achievements", 54);
    }

    @Override
    protected void onGUIInventoryClick(InventoryClickEvent event) {

    }

    @Override
    protected void populate() {
        Inventory inventory = getProxyInventory();

        int index = 0;
        for(Achievement achievement : ModuleAchievement.get().getManager().getAchievements()){
            AchievementItem item = new AchievementItem(achievement, player);
           inventory.setItem(index, item.getItem());
            index++;
        }
    }
}
