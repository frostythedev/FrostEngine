package me.frostythedev.frostengine.modules.ranks.callback;

import com.google.common.collect.Maps;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.data.core.Callback;
import me.frostythedev.frostengine.modules.ranks.objects.Rank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class RankLoadCallback implements Callback {

    private Map<String, Rank> ranks;

    @Override
    public void read(ResultSet rs) throws SQLException {
        ranks = Maps.newHashMap();

        if(rs.next()){
            while (rs.next()){
                Rank rank = FEPlugin.getGson().fromJson(rs.getString("data"), Rank.class);
                if(rank != null){
                    ranks.put(rank.getName(), rank);
                }
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
    public Object result() {
        return ranks;
    }
}
