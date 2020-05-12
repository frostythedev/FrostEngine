package me.frostythedev.frostengine.modules.gameapi.arenas;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.utils.LogUtils;
import me.frostythedev.frostengine.config.BukkitDocument;
import me.frostythedev.frostengine.data.core.Database;
import me.frostythedev.frostengine.data.core.DatabaseField;
import me.frostythedev.frostengine.data.mysql.MySQL;
import me.frostythedev.frostengine.modules.gameapi.arenas.data.GameArenaGatherCallback;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import me.frostythedev.frostengine.modules.gameapi.exception.ArenaAlreadyLoadedException;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ArenaManager {

    private Map<String, GameArena> arenas;
    private Map<Integer, String> arenaIds;

    //Figure out away to load arenaNames ones, and only update it whenever it is
    // needed to be updated to avoid using too much memory creating a new List every
    // time getArenaNames is called
    //private List<String> arenaNames;

    private Game minigame;
    
    @Inject
    Database database;
    
    @Inject
    FEPlugin plugin;

    public ArenaManager(Game minigame) {
        this.arenas = Maps.newHashMap();
        this.arenaIds = Maps.newHashMap();
        //this.arenaNames = new ArrayList<>();

        this.minigame = minigame;
    }

    public int getSize(){
        return getArenas().size();
    }

    public GameArena getLobbyArena(){
        for(GameArena arena : getArenas()){
            if(arena.isLobby()) return arena;
        }
        return null;
    }

    public GameArena getFreeArena() {
        for (GameArena arena : getArenas()) {
            if (!arena.isInUse() && !arena.isLobby()) {
                return arena;
            }
        }
        return null;
    }

    public GameArena getFreeArena(int spawnSize) {
        for (GameArena arena : getArenas()) {
            if (!arena.isInUse() && !arena.isLobby() && arena.getSize() >= spawnSize) {
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
        if(database.hasConnection()){
            GameArenaGatherCallback gaCallback = new GameArenaGatherCallback(minigame);
            database.syncQuery("SELECT * FROM " + DatabaseField.ARENA_TABLE +
                            " WHERE minigameName=?"
                    , new Object[]{minigame.getName()}, gaCallback);

            if(gaCallback.result() != null && !gaCallback.result().isEmpty()){
                gaCallback.result().values().forEach(arena -> arenaIds.put(arena.getId(), arena.getArenaName()));
                this.arenas = gaCallback.result();
            }
        }
    }

    public void loadArenas(File folder) throws ArenaAlreadyLoadedException {
        if(!folder.exists()){
            return;
        }

        if(folder.isDirectory()){

            File[] files = folder.listFiles();
            if(files != null){
                for(File file : files){
                    BukkitDocument document = BukkitDocument.of(file.getAbsolutePath());
                    if(!document.getKeys(false).isEmpty()){
                        for(String key : document.getKeys(false)){
                            GameArena arena = plugin.getGson().fromJson(key, GameArena.class);
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

        }else{

            BukkitDocument document = BukkitDocument.of(folder.getAbsolutePath());
            if(!document.getKeys(false).isEmpty()){
              for(String key : document.getKeys(false)){
                GameArena arena = plugin.getGson().fromJson(key, GameArena.class);
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
                   LogUtils.info("Could not connect to MySQL server to load arenaData!");
               }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String query = "SELECT * FROM `" + DatabaseField.ARENA_TABLE + "` " +
                    "WHERE minigameName=%s;";
            query = String.format(query, this.minigame.getName());

            ResultSet rs = mySQL.syncQuery(query);

            if (rs != null) {
                try {
                    if (rs.next()) {
                        do {
                            GameArena arena = plugin.getGson().fromJson(
                                    rs.getString("data"), GameArena.class);

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

                    if (rs.next()) {
                        while (rs.next()) {
                            GameArena arena = plugin.getGson().fromJson(
                                    rs.getString("data"), GameArena.class);

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

    public List<String> getArenaNames(){
        return Lists.newArrayList(arenas.keySet());
    }
}
