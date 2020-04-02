package me.frostythedev.frostengine.modules.achievements.api;

import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.data.core.Callback;
import me.frostythedev.frostengine.modules.achievements.player.AchievementPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AchievementPlayerCallback implements Callback<AchievementPlayer> {

    private AchievementPlayer achievementPlayer;

    @Override
    public void read(ResultSet rs) throws SQLException {

        if (rs == null) {
            return;
        }

        while (rs.next()) {
            AchievementPlayer player = FEPlugin.getGson().fromJson(rs.getString("data"), AchievementPlayer.class);
            if (player != null) {
                this.achievementPlayer = player;
            }
        }
    }

    @Override
    public void digestSync() {

    }

    @Override
    public void digestAsync() {

    }

    @Override
    public AchievementPlayer result() {
        return achievementPlayer;
    }
}
