package gameapi.kits;

import com.google.common.collect.Maps;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.utilities.LogUtils;
import me.frostythedev.frostengine.data.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class KitManager {

    private Map<Integer, GameKit> kits;
    private Map<String, Integer> kitsId;

    public KitManager() {
        this.kits = Maps.newHashMap();
        this.kitsId = Maps.newHashMap();
    }

    public GameKit getKit(int id) {
        if (kits.containsKey(id)) {
            return kits.get(id);
        }
        return null;
    }

    public GameKit getKit(String name) {
        if (kitsId.containsKey(name)) {
            return getKit(kitsId.get(name));
        }
        return null;
    }

    public void loadKits(MySQL mySQL) {
        if (!mySQL.hasConnection()) {
            try {
                mySQL.openConnection();
                loadKits(mySQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {

            String query = "SELECT * FROM `fe_kits`;";
            ResultSet rs = mySQL.syncQuery(query);
            if (rs != null) {
                try {
                    if (rs.next()) {
                        while (rs.next()) {
                            GameKit gameKit = FEPlugin.getGson().fromJson(rs.getString("data"), GameKit.class);
                            if (gameKit != null) {
                                if (kits.containsKey(gameKit.getId())) {
                                    LogUtils.severe("A kit is already loaded with the id '" + gameKit.getId() + "'");
                                } else {
                                    kits.put(gameKit.getId(), gameKit);
                                    kitsId.put(gameKit.getName(), gameKit.getId());
                                }
                            }
                        }
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

    public Map<Integer, GameKit> getKits() {
        return kits;
    }
}
