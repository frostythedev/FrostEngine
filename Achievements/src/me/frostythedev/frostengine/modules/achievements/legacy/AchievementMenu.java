/*
package me.frostythedev.frostengine.modules.achievements.menu;

import me.frostythedev.frostengine.bukkit.menu.ItemMenu;
import me.frostythedev.frostengine.modules.achievements.core.Achievement;
import me.frostythedev.frostengine.modules.achievements.ModuleAchievement;
import org.bukkit.entity.Player;

public class AchievementMenu extends ItemMenu {

    public AchievementMenu(Player player) {
        super("Achievements", 6);

        int index = 0;
        for(Achievement achievement : ModuleAchievement.get().getManager().getAchievements()){
            AchievementItem item = new AchievementItem(achievement, player);
            this.addMenuItem(item, index);
            index++;
        }
    }
}
*/
