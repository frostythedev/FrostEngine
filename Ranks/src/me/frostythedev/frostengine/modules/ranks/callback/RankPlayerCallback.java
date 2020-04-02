package me.frostythedev.frostengine.modules.ranks.callback;

import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.data.core.Callback;
import me.frostythedev.frostengine.modules.ranks.objects.RankPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RankPlayerCallback implements Callback {

    private RankPlayer rankPlayer;

    @Override
    public void read(ResultSet rs) throws SQLException {
        if(!rs.next()){
            return;
        }

        RankPlayer rankPlayer = FEPlugin.getGson().fromJson(rs.getString("data"), RankPlayer.class);
        if(rankPlayer != null){
            this.rankPlayer = rankPlayer;
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
        return rankPlayer;
    }
}
