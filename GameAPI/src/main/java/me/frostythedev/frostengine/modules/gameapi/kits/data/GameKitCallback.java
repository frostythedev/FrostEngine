package me.frostythedev.frostengine.modules.gameapi.kits.data;

import com.google.inject.Inject;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.data.core.Callback;
import me.frostythedev.frostengine.modules.gameapi.kits.GameKit;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameKitCallback implements Callback<GameKit> {

    @Inject FEPlugin plugin;

    private GameKit result;

    @Override
    public void read(ResultSet rs) throws SQLException {
        if(rs != null){
            result = plugin.getGson().fromJson(rs.getString("data"), GameKit.class);
            if(result.getId() < 0){
                result.setId(rs.getInt("id"));
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
    public GameKit result() {
        return result;
    }
}
