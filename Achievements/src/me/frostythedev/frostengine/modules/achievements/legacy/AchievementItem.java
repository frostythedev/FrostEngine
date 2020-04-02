/*
package me.frostythedev.frostengine.modules.achievements.menu;

import com.google.common.collect.Lists;
import me.frostythedev.frostengine.modules.achievements.api.AchievementAPI;
import me.frostythedev.frostengine.bukkit.menu.MenuItem;
import me.frostythedev.frostengine.modules.achievements.core.Achievement;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.material.MaterialData;

import java.util.List;

public class AchievementItem extends MenuItem {

    private final MaterialData UNLOCKED = new MaterialData(Material.DIAMOND);
    private final MaterialData LOCKED = new MaterialData(Material.COAL_BLOCK);

    private Achievement achievement;
    private Player player;

    public AchievementItem(Achievement achievement, Player player) {
        this.achievement = achievement;
        this.player = player;

       if(isUnlocked()){
           setText("&a" + achievement.getName());
           setIcon(UNLOCKED);

           List<String> desc = Lists.newArrayList(achievement.getDescription());
           desc.add(" ");
           desc.add("&eUnlocked: &a" + AchievementAPI.getFormattedUnlockedTime(player, achievement));
           setDescriptions(desc);
       }else{
           setText("&c" + achievement.getName());
           setIcon(LOCKED);

           List<String> desc = Lists.newArrayList(achievement.getDescription());
           desc.add(" ");
           desc.add("&7&oNot yet unlocked.");
           setDescriptions(desc);
       }
    }

    private boolean isUnlocked(){
        return AchievementAPI.hasAchievement(player, achievement);
    }

    @Override
    public void onClick(Player player, ClickType type) {

    }
}
*/
