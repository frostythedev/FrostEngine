package me.frostythedev.frostengine.modules.gameapi.arenas.data;

import com.google.common.collect.Maps;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.utils.LogUtils;
import me.frostythedev.frostengine.modules.gameapi.Minigame;
import me.frostythedev.frostengine.modules.gameapi.arenas.GameArena;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import me.frostythedev.frostengine.modules.gameapi.exception.ArenaAlreadyLoadedException;
import me.frostythedev.frostengine.data.core.Callback;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class GameArenaGatherCallback implements Callback<Map<String, GameArena>>{

    private Map<String, GameArena> arenas;

    private Game minigame;

    public GameArenaGatherCallback(Game minigame) {
        this.minigame = minigame;
    }

    @Override
    public void read(ResultSet rs) throws SQLException {
        arenas = Maps.newHashMap();

        if(rs != null){
            if (rs.next()) {

                do {

                    if(FEPlugin.getGson() == null){
                        System.out.println("[ARENAGATHER] GSON has not bee loaded yet");
                        return;
                    }else{
                        System.out.println("[ARENAGATHER] GSON IS LOADED");
                    }

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

                //IDK WHAT THIS IS
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
