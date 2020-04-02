package me.frostythedev.frostengine.modules.ranks.callback;

import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.data.core.Callback;
import me.frostythedev.frostengine.modules.ranks.objects.Rank;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SingleRankLoadCallback implements Callback {

    private Rank rank;

    @Override
    public void read(ResultSet rs) throws SQLException {
        if(!rs.next()){
            return;
        }

        rank = FEPlugin.getGson().fromJson(rs.getString("data"), Rank.class);
    }

    @Override
    public void digestSync() {

    }

    @Override
    public void digestAsync() {

    }

    @Override
    public Object result() {
        return rank;
    }
}
