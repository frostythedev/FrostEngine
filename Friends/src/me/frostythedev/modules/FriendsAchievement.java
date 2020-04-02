package me.frostythedev.modules;

import me.frostythedev.frostengine.modules.achievements.core.Achievement;
import me.frostythedev.frostengine.modules.achievements.core.AchievementType;
import me.frostythedev.frostengine.modules.achievements.core.AchievementTypeEnum;

import java.util.Arrays;

public class FriendsAchievement extends Achievement {

    public FriendsAchievement() {
        super(100, "Socially Active", true, AchievementTypeEnum.GENERAL.getName());
        setDescription(Arrays.asList("Make at least 1 friend on this server using:", "/friend accept <name>"));
    }
}
