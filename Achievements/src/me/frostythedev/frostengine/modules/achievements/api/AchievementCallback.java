package me.frostythedev.frostengine.modules.achievements.api;

import me.frostythedev.frostengine.modules.achievements.core.Achievement;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.data.core.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AchievementCallback implements Callback<Achievement> {

    private Achievement achievement;

    @Override
    public void read(ResultSet rs) throws SQLException {

        if (rs == null) {
            return;
        }
        while (rs.next()) {
            Achievement achievement = FEPlugin.getGson().fromJson(rs.getString("data"), Achievement.class);
            if (achievement != null) {
                this.achievement = achievement;
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
    public Achievement result() {
        return achievement;
    }
}
