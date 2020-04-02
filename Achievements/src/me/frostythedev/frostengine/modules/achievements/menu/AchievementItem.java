package me.frostythedev.frostengine.modules.achievements.menu;

import com.google.common.collect.Lists;
import me.frostythedev.frostengine.bukkit.utils.items.ItemBuilder;
import me.frostythedev.frostengine.modules.achievements.api.AchievementAPI;
import me.frostythedev.frostengine.modules.achievements.core.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class AchievementItem {

   private Achievement achievement;
    private UUID playerUUid;

    public AchievementItem(Achievement achievement, Player player) {
        this.achievement = achievement;
        this.playerUUid = player.getUniqueId();
    }


    public ItemStack getItem(){
        ItemBuilder itemBuilder = new ItemBuilder(Material.COAL_BLOCK);
        List<String> desc = Lists.newArrayList(achievement.getDescription());

        if(AchievementAPI.hasAchievement(Bukkit.getPlayer(playerUUid), this.achievement)) {
            itemBuilder.ofType(Material.DIAMOND);
            itemBuilder.withCustomName("&a" + achievement.getName());


            desc.add(" ");
            desc.add("&eUnlocked: &a" + AchievementAPI.getFormattedUnlockedTime(Bukkit.getPlayer(playerUUid), achievement));
            itemBuilder.withLore(desc);

        }else{
            itemBuilder.withCustomName("&c" + achievement.getName());

            desc.add(" ");
            desc.add("&7&oNot yet unlocked.");
            itemBuilder.withLore(desc);
        }

        return itemBuilder.build();
    }

}
