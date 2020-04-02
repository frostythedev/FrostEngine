package me.frostythedev.frostengine.modules.achievements.core;

import me.frostythedev.frostengine.modules.achievements.ModuleAchievement;

public class AchievementType {

    public static boolean isType(String name){
        return ModuleAchievement.get().getConfig().getStringList("types").contains(name);
    }
}
