package me.frostythedev.frostengine.bukkit.gameapi.arenas;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.frostythedev.frostengine.bukkit.gameapi.Minigame;
import me.frostythedev.frostengine.data.core.Database;
import me.frostythedev.frostengine.data.core.DatabaseField;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public void loadArenas(){
        GameArenaGatherCallback gaCallback = new GameArenaGatherCallback(minigame);
        Database.get().syncQuery("SELECT * FROM " + DatabaseField.ARENA_TABLE, null, gaCallback);
        if(gaCallback.result() != null && !gaCallback.result().isEmpty()){
            gaCallback.result().values().forEach(arena -> arenaIds.put(arena.getId(), arena.getArenaName()));
            this.arenas = gaCallback.result();
        }
    }

    /*public void loadArenas(File folder) throws ArenaAlreadyLoadedException {
        if(!folder.exists()){
            return;
        }

        if(folder.isDirectory()){
            File[] files = folder.listFiles();
            for(File file : files){
                BukkitDocument document = BukkitDocument.of(file.getAbsolutePath());
                if(!document.getKeys(false).isEmpty()){
                    for(String key : document.getKeys(false)){
                        GameArena arena = FEPlugin.getGson().fromJson(key, GameArena.class);
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
            }
        }else{
            BukkitDocument document = BukkitDocument.of(folder.getAbsolutePath());
            if(!document.getKeys(false).isEmpty()){
              for(String key : document.getKeys(false)){
                GameArena arena = FEPlugin.getGson().fromJson(key, GameArena.class);
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
        }
    }

    public void loadArenas(MySQL mySQL) throws ArenaAlreadyLoadedException {
        if (!mySQL.hasConnection()) {
            try {
               if(mySQL.openConnection()){
                   loadArenas(mySQL);
               }else{
                   //TODO Error logging
               }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String query = "SELECT * FROM `" + DatabaseField.ARENA_TABLE + "`;";
            ResultSet rs = mySQL.syncQuery(query);

            if (rs != null) {
                try {

                    if (rs.next()) {
                        do {
                            GameArena arena = FEPlugin.getGson().fromJson(rs.getString("data"), GameArena.class);
                            if (arena != null) {
                                arena.setMinigame(minigame);
                                if (getArena(arena.getArenaName()) != null) {
                                    throw new ArenaAlreadyLoadedException(arena.getArenaName());
                                } else {
                                    arenas.put(arena.getArenaName(), arena);
                                    arenaIds.put(arena.getId(), arena.getArenaName());
                                }
                            }
                        } while (rs.next());

                    *//*if (rs.next()) {
                        while (rs.next()) {
                            GameArena arena = FEPlugin.getGson().fromJson(rs.getString("data"), GameArena.class);
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
                    }*//*
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
    }*/


    public Collection<GameArena> getArenas() {
        return arenas.values();
    }

    public List<String> getArenaNames(){
        return Lists.newArrayList(arenas.keySet());
    }
}
