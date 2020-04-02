package me.frostythedev.frostengine.bukkit.gameapi.arenas;

import com.google.common.collect.Maps;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.gameapi.Minigame;
import me.frostythedev.frostengine.bukkit.gameapi.exception.ArenaAlreadyLoadedException;
import me.frostythedev.frostengine.data.core.Callback;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class GameArenaGatherCallback implements Callback<Map<String, GameArena>>{

    private Map<String, GameArena> arenas;

    private Minigame minigame;

    public GameArenaGatherCallback(Minigame minigame) {
        this.minigame = minigame;
    }

    @Override
    public void read(ResultSet rs) throws SQLException {
        arenas = Maps.newHashMap();

        if(rs != null){
            if (rs.next()) {

                do {
                    GameArena arena = FEPlugin.getGson().fromJson(rs.getString("data"), GameArena.class);
                    if (arena != null) {
                        if(arena.getId() < 0){
                            arena.setId(rs.getInt("id"));
                        }
                        arena.setMinigame(minigame);
                        if (arenas.containsKey(arena.getArenaName())) {
                            try {
                                throw new ArenaAlreadyLoadedException(arena.getArenaName());
                            } catch (ArenaAlreadyLoadedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            arenas.put(arena.getArenaName(), arena);
                            Bukkit.getLogger().info("Added arena with id: " + arena.getId());
                        }
                    }
                }while (rs.next());

                /*while (rs.next()) {
                    GameArena arena = FEPlugin.getGson().fromJson(rs.getString("data"), GameArena.class);
                    if (arena != null) {
                        if(arena.getId() < 0){
                            arena.setId(rs.getInt("id"));
                        }
                        arena.setMinigame(minigame);
                        if (result.containsKey(arena.getArenaName())) {
                            try {
                                throw new ArenaAlreadyLoadedException(arena.getArenaName());
                            } catch (ArenaAlreadyLoadedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            result.put(arena.getArenaName(), arena);
                        }
                    }
                }*/
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
    public Map<String, GameArena> result() {
        return arenas;
    }
}
