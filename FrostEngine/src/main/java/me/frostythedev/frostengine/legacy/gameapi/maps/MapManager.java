package me.frostythedev.frostengine.legacy.gameapi.maps;

import com.google.common.collect.Maps;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.utils.LogUtils;
import me.frostythedev.frostengine.data.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MapManager {

    private Map<Integer, GameMap> maps;
    private Map<String, Integer> mapIds;

    public MapManager() {
        this.maps = Maps.newHashMap();
        this.mapIds = Maps.newHashMap();
    }

    public GameMap getMap(int id) {
        if (maps.containsKey(id)) {
            return maps.get(id);
        }
        return null;
    }

    public GameMap getMap(String name) {
        if (mapIds.containsKey(name)) {
            return getMap(mapIds.get(name));
        }
        return null;
    }

    public void loadMaps(MySQL mySQL) {
        if (!mySQL.hasConnection()) {
            try {
                if(mySQL.openConnection()){
                    loadMaps(mySQL);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {

            String query = "SELECT * FROM `fe_maps`;";
            ResultSet rs = mySQL.syncQuery(query);
            if (rs != null) {
                try {
                    if (rs.next()) {
                        do {
                            GameMap gameMap = FEPlugin.getGson().fromJson(rs.getString("data"), GameMap.class);
                            if (gameMap != null) {
                                if (maps.containsKey(gameMap.getId())) {
                                    LogUtils.severe("A map is already loaded with the id '" + gameMap.getId() + "'");
                                } else {
                                    maps.put(gameMap.getId(), gameMap);
                                    mapIds.put(gameMap.getName(), gameMap.getId());
                                }
                            }
                        }while (rs.next());

                        /*while (rs.next()) {
                            GameMap gameMap = FEPlugin.getGson().fromJson(rs.getString("data"), GameMap.class);
                            if (gameMap != null) {
                                if (maps.containsKey(gameMap.getId())) {
                                    LogUtils.severe("A map is already loaded with the id '" + gameMap.getId() + "'");
                                } else {
                                    maps.put(gameMap.getId(), gameMap);
                                    mapIds.put(gameMap.getName(), gameMap.getId());
                                }
                            }
                        }*/
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Map<Integer, GameMap> getMaps() {
        return maps;
    }
}
