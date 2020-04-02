package me.frostythedev.frostengine.modules.achievements.manager;

import com.google.common.collect.Maps;
import me.frostythedev.frostengine.modules.achievements.core.Achievement;

import java.util.Collection;
import java.util.Map;

public class AchievementManager {

    private Map<String, Achievement> achievements;
    private Map<Integer, String> idMap;

    public AchievementManager() {
        this.achievements = Maps.newHashMap();
        this.idMap = Maps.newHashMap();
    }

    public void loadAchievement(String name, Achievement achievement){
        this.achievements.put(name, achievement);
        this.idMap.put(achievement.getId(), achievement.getName());
    }

    public void unloadAchievement(String name){
        if(isAchievement(name)){
            this.achievements.remove(name);
        }
    }

    public Achievement getAchievement(String name){
        return this.achievements.get(name);
    }

    public Achievement getAchievement(int id){
        if(idMap.containsKey(id)){
            return getAchievement(idMap.get(id));
        }
        return null;
    }

    public boolean isAchievement(String name){
        return getAchievement(name) != null;
    }

    public Collection<Achievement> getAchievements(){
        return this.achievements.values();
    }

    public void setAchievements(Map<String, Achievement> achievements) {
        this.achievements = achievements;
    }
}
