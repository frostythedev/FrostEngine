package me.frostythedev.frostengine.modules.achievements.tasks.achievements;

import me.frostythedev.frostengine.modules.achievements.core.Achievement;
import me.frostythedev.frostengine.modules.achievements.ModuleAchievement;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.data.core.Database;
import me.frostythedev.frostengine.data.core.DatabaseField;
import me.frostythedev.frostengine.modules.achievements.api.AchievementIdCallback;

public class AchievementSaver implements Runnable {

    private Achievement achievement;

    public AchievementSaver(Achievement achievement) {
        this.achievement = achievement;
    }

    @Override
    public void run() {
        if (ModuleAchievement.get().getManager().isAchievement(achievement.getName())) {
            return;
        } else {
            Database.get().syncUpdate("INSERT INTO " + DatabaseField.ACHIEVEMENT_TABLE + "(data) VALUES (?)", new Object[]
                    {FEPlugin.getGson().toJson(achievement)});

            AchievementIdCallback idCallback = new AchievementIdCallback();
            Database.get().syncQuery("SELECT id FROM " +
                            DatabaseField.ACHIEVEMENT_TABLE + " WHERE data=?",
                    new Object[]{FEPlugin.getGson().toJson(achievement)}, idCallback);


            achievement.setId(idCallback.result());
            ModuleAchievement.get().getManager().loadAchievement(achievement.getName(), achievement);
        }
    }
}
