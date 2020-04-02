package me.frostythedev.frostengine.modules.achievements.tasks.players;

import me.frostythedev.frostengine.modules.achievements.player.AchievementPlayer;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.data.core.Database;
import me.frostythedev.frostengine.data.core.DatabaseField;

public class PlayerSaver implements Runnable {

    private AchievementPlayer player;

    public PlayerSaver(AchievementPlayer player) {
        this.player = player;
    }

    @Override
    public void run() {
        Database.get().syncUpdate("UPDATE " + DatabaseField.ACHIEVEMENT_PLAYERS_TABLE + " SET data=? WHERE uuid=?",
                new Object[]{FEPlugin.getGson().toJson(player), player.getUuid().toString()});
    }
}
