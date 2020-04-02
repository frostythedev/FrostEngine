package me.frostythedev.frostengine.modules.achievements.api;

import me.frostythedev.frostengine.data.core.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AchievementIdCallback implements Callback<Integer> {

    private int result;

    @Override
    public void read(ResultSet rs) throws SQLException {
        if (rs == null) {
            return;
        }

        this.result = rs.getInt("id");
    }

    @Override
    public void digestSync() {

    }

    @Override
    public void digestAsync() {

    }

    @Override
    public Integer result() {
        return result;
    }
}
