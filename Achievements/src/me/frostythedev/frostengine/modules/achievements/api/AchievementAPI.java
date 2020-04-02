package me.frostythedev.frostengine.modules.achievements.api;

import me.frostythedev.frostengine.modules.achievements.core.Achievement;
import me.frostythedev.frostengine.modules.achievements.player.AchievementPlayer;
import me.frostythedev.frostengine.modules.achievements.ModuleAchievement;
import me.frostythedev.frostengine.modules.achievements.player.PlayerManager;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AchievementAPI {

    private static PlayerManager pm = ModuleAchievement.get().getPlayerManager();

    public static boolean hasAchievement(Player player, Achievement achievement) {
        if(pm.isLoaded(player)){
            return pm.getPlayer(player).hasUnlocked(achievement.getName());
        }else{
            pm.loadPlayer(player, new AchievementPlayer(player));
            return pm.getPlayer(player).hasUnlocked(achievement.getName());
        }
    }

    public static String getFormattedUnlockedTime(Player player, Achievement achievement) {
        if (hasAchievement(player, achievement)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd");
            return dateFormat.format(new Date(getUnlockedTime(player, achievement)));
        } else {
            return "Not Unlocked";
        }

    }

    public static long getUnlockedTime(Player player, Achievement achievement) {
        if(pm.isLoaded(player)){
            return pm.getPlayer(player).getUnlockedTime(achievement.getName());
        }else{
            pm.loadPlayer(player, new AchievementPlayer(player));
            return pm.getPlayer(player).getUnlockedTime(achievement.getName());
        }

    }


    public static boolean giveAchievement(Player player, Achievement achievement) {
        return achievement.give(player);
    }

    public static boolean giveAchievement(Player player, String name) {
        return giveAchievement(player, ModuleAchievement.get().getManager().getAchievement(name));
    }
}
