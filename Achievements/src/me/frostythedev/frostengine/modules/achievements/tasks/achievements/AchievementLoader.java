package me.frostythedev.frostengine.modules.achievements.tasks.achievements;

import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.thread.Tasks;
import me.frostythedev.frostengine.config.BukkitDocument;
import me.frostythedev.frostengine.data.core.Database;
import me.frostythedev.frostengine.data.core.DatabaseField;
import me.frostythedev.frostengine.modules.achievements.core.Achievement;
import me.frostythedev.frostengine.modules.achievements.ModuleAchievement;
import me.frostythedev.frostengine.modules.achievements.api.AchievementLoadCallback;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;

public class AchievementLoader implements Runnable {

    @Override
    public void run() {
        AchievementLoadCallback loadCallback = new AchievementLoadCallback();
        Database.get().syncQuery("SELECT * FROM " + DatabaseField.ACHIEVEMENT_TABLE.toString(), null, loadCallback);
        if (loadCallback.result() != null && !loadCallback.result().isEmpty()) {
            ModuleAchievement.get().getManager().setAchievements(loadCallback.result());
        }else{
            Collection<File> achievementFiles = FileUtils.listFiles(new File(Achievement.SAVE_DIRECTORY), null, false);

            for (File file : achievementFiles) {
                BukkitDocument document = BukkitDocument.of(file.getAbsolutePath());
                document.load();

                if (document.getString("data") != null) {
                    Achievement achievement = FEPlugin.getGson().fromJson(document.getString("data"), Achievement.class);
                    if (achievement != null) {
                        Tasks.run(new AchievementSaver(achievement));
                    }
                }
            }
        }
    }
}
