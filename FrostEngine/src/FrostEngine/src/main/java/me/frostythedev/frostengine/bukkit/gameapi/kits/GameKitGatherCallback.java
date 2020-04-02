package me.frostythedev.frostengine.bukkit.gameapi.kits;

import com.google.common.collect.Maps;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.debug.Debugger;
import me.frostythedev.frostengine.data.core.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class GameKitGatherCallback implements Callback<Map<Integer, GameKit>>{

    private Map<Integer, GameKit> kits;

    @Override
    public void read(ResultSet rs) throws SQLException {
        kits = Maps.newHashMap();

        if(rs != null){
            if (rs.next()) {
                do {
                    GameKit gameKit = FEPlugin.getGson().fromJson(rs.getString("data"), GameKit.class);
                    if (gameKit != null) {
                        if (kits.containsKey(gameKit.getId())) {
                            Debugger.info("A kit is already loaded with the id '" + gameKit.getId() + "'");
                        } else {
                            kits.put(gameKit.getId(), gameKit);
                        }
                    }
                } while (rs.next());
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
    public Map<Integer, GameKit> result() {
        return kits;
    }
}
