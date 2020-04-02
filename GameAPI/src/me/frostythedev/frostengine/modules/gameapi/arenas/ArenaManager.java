package gameapi.arenas;

import com.google.common.collect.Maps;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.data.mysql.MySQL;
import gameapi.Minigame;
import gameapi.exception.ArenaAlreadyLoadedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

public class ArenaManager {

    private Map<String, GameArena> arenas;
    private Map<Integer, String> arenaIds;

    private Minigame minigame;

    public ArenaManager(Minigame minigame) {
        this.arenas = Maps.newHashMap();
        this.arenaIds = Maps.newHashMap();

        this.minigame = minigame;
    }

    public int getSize(){
        return getArenas().size();
    }

    public GameArena getFreeArena() {
        for (GameArena arena : getArenas()) {
            if (!arena.isInUse()) {
                return arena;
            }
        }
        return null;
    }

    public GameArena getFreeArena(int spawnSize) {
        for (GameArena arena : getArenas()) {
            if (!arena.isInUse() && arena.getSize() >= spawnSize) {
                return arena;
            }
        }
        return null;
    }

    public GameArena getArena(String name) {
        if (arenas.containsKey(name)) {
            return arenas.get(name);
        }
        return null;
    }

    public GameArena getArena(int id) {
        if (arenaIds.containsKey(id)) {
            return getArena(arenaIds.get(id));
        }
        return null;
    }

    public void loadArenas(MySQL mySQL) throws ArenaAlreadyLoadedException {
        if (!mySQL.hasConnection()) {
            try {
                mySQL.openConnection();
                loadArenas(mySQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String query = "SELECT * FROM `fe_arenas`;";
            ResultSet rs = mySQL.syncQuery(query);

            if (rs != null) {
                try {
                    if (rs.next()) {
                        while (rs.next()) {
                            GameArena arena = FEPlugin.getGson().fromJson("data", GameArena.class);
                            if (arena != null) {
                                arena.setMinigame(minigame);
                                if (getArena(arena.getArenaName()) != null) {
                                    throw new ArenaAlreadyLoadedException(arena.getArenaName());
                                } else {
                                    arenas.put(arena.getArenaName(), arena);
                                    arenaIds.put(arena.getId(), arena.getArenaName());
                                }
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public Collection<GameArena> getArenas() {
        return arenas.values();
    }
}
