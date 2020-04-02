package me.frostythedev.frostengine.modules.achievements.api;

import com.google.common.collect.Maps;
import me.frostythedev.frostengine.modules.achievements.core.Achievement;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.data.core.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class AchievementLoadCallback implements Callback<Map<String, Achievement>> {

    private Map<String, Achievement> achievementMap;

    @Override
    public void read(ResultSet rs) throws SQLException {
        this.achievementMap = Maps.newHashMap();
        while (rs.next()) {
            Achievement achievement = FEPlugin.getGson().fromJson(rs.getString("data"), Achievement.class);
            if (achievement != null) {
                if(achievement.getId() == -1){
                    achievement.setId(rs.getInt("id"));
                }
                achievementMap.put(achievement.getName(), achievement);
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
    public Map<String, Achievement> result() {
        return achievementMap;
    }
}
