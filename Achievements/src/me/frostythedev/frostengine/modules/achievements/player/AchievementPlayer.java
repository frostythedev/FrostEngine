package me.frostythedev.frostengine.modules.achievements.player;

import com.google.common.collect.Maps;
import me.frostythedev.frostengine.modules.achievements.core.Achievement;
import me.frostythedev.frostengine.config.BukkitDocument;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class AchievementPlayer {

    private UUID uuid;
    private Map<String, Achievement> achievements;
    private Map<String, Long> unlockedDates;

    public AchievementPlayer(Player player) {
        this.uuid = player.getUniqueId();
        this.achievements = Maps.newHashMap();
        this.unlockedDates = Maps.newHashMap();
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean hasUnlocked(String name) {
        return achievements.containsKey(name);
    }

    @Deprecated
    public boolean hasUnlocked(Achievement achievement) {
        return achievements.containsValue(achievement);
    }

    public long getUnlockedTime(String name) {
        if(hasUnlocked(name)){
            return unlockedDates.get(name);
        }
        return -1;
    }

    @Deprecated
    public long getUnlockedTime(Achievement achievement) {
        if(hasUnlocked(achievement)){
            return unlockedDates.get(achievement.getName());
        }
        return -1;
    }

    public void unlock(Achievement achievement, boolean save){
        if(!hasUnlocked(achievement.getName())){
            achievements.put(achievement.getName(), achievement);
            unlockedDates.put(achievement.getName(), System.currentTimeMillis());
        }
    }

    protected void into(BukkitDocument document){
        for(Achievement achievement : achievements.values()){
            document.set(String.valueOf(achievement.getId()), getUnlockedTime(achievement.getName()));
        }
        document.save();
    }

    public Map<String, Achievement> getAchievements() {
        return achievements;
    }

    public Map<String, Long> getUnlockedDates() {
        return unlockedDates;
    }
}
