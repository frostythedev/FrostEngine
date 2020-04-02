package me.frostythedev.frostengine.bukkit.gameapi.arenas;

import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.data.core.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameArenaCallback implements Callback<GameArena> {

    private GameArena result;

    @Override
    public void read(ResultSet rs) throws SQLException {
        if(rs != null){
            GameArena result = FEPlugin.getGson().fromJson(rs.getString("data"), GameArena.class);
            if(result != null && result.getId() < 0){
                result.setId(rs.getInt("id"));
                this.result = result;
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
    public GameArena result() {
        return result;
    }
}
