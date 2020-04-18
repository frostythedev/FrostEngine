package me.frostythedev.frostengine.modules.gameapi.kits;

import com.google.common.collect.Maps;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.debug.Debugger;
import me.frostythedev.frostengine.modules.gameapi.Minigame;
import me.frostythedev.frostengine.config.BukkitDocument;
import me.frostythedev.frostengine.data.core.Database;
import me.frostythedev.frostengine.data.core.DatabaseField;
import me.frostythedev.frostengine.modules.gameapi.kits.data.GameKitGatherCallback;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Map;
import java.util.UUID;

public class KitManager {

    private Minigame minigame;

    private Map<Integer, GameKit> kits;
    private Map<String, Integer> kitsId;
    private Map<UUID, String> playerKits;

    public KitManager(Minigame minigame) {
        this.minigame = minigame;
        this.kits = Maps.newHashMap();
        this.kitsId = Maps.newHashMap();
        this.playerKits = Maps.newHashMap();
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

    public GameKit getKit(Player player) {
        if (playerKits.containsKey(player.getUniqueId())) {
            return getKit(playerKits.get(player.getUniqueId()));
        }
        return null;
    }

    public boolean setKit(Player player, String newKitName) {
        if (getKit(player) != null) {
            if (getKit(newKitName) != null) {
                playerKits.replace(player.getUniqueId(), newKitName);
                return true;
            }
        } else {
            playerKits.put(player.getUniqueId(), newKitName);
            return true;
        }

        return false;
    }

    public void giveAllKits() {
        if (!playerKits.isEmpty()) {
            for (UUID uuid : playerKits.keySet()) {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null && player.isOnline()) {
                    getKit(playerKits.get(uuid)).giveKit(player);
                }
            }
        }
    }

    public void loadKits() {
        GameKitGatherCallback gkCallback = new GameKitGatherCallback();
        Database.get().syncQuery("SELECT * FROM " + DatabaseField.KIT_TABLE +
                " WHERE minigameName=?",
                new Object[]{minigame.getName()}, gkCallback);

        if (gkCallback.result() != null && !gkCallback.result().isEmpty()) {
            gkCallback.result().values().forEach(kit -> kitsId.put(kit.getName(), kit.getId()));
            this.kits = gkCallback.result();
        }
    }

    public boolean createKit(GameKit gameKit) {
        if (getKit(gameKit.getName()) != null) return false;

        Database.get().asyncSyncInsert("INSERT INTO " + DatabaseField.KIT_TABLE + "(minigameName, kitName,data)" +
                " VALUES (?,?,?)",
                new Object[]{minigame.getName(),  gameKit.getName(),
                        gameKit.serialize().toString()});
        return true;
    }

    public void loadKits(File folder) {
        if (!folder.exists()) {
            return;
        }

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    BukkitDocument document = BukkitDocument.of(file.getAbsolutePath());
                    if (!document.getKeys(false).isEmpty()) {
                        for (String key : document.getKeys(false)) {
                            GameKit gameKit = FEPlugin.getGson().fromJson(key, GameKit.class);
                            if (gameKit != null) {
                                if (kits.containsKey(gameKit.getId())) {
                                    Debugger.info("A kit is already loaded with the id '" + gameKit.getId() + "'");
                                } else {
                                    kits.put(gameKit.getId(), gameKit);
                                    kitsId.put(gameKit.getName(), gameKit.getId());
                                }
                            }
                        }
                    }
                }
            } else {
                Debugger.info("Could not find any files in this folder");
            }
        }

        Debugger.info("Could not locate a folder for this file. ");
        /*else{
            BukkitDocument document = BukkitDocument.of(folder.getAbsolutePath());
            if(!document.getKeys(false).isEmpty()){
                for(String key : document.getKeys(false)){
                    GameKit gameKit = FEPlugin.getGson().fromJson(key, GameKit.class);
                    if (gameKit != null) {
                        if (kits.containsKey(gameKit.getId())) {
                            Debugger.info("A kit is already loaded with the id '" + gameKit.getId() + "'");
                        } else {
                            kits.put(gameKit.getId(), gameKit);
                            kitsId.put(gameKit.getName(), gameKit.getId());
                        }
                    }
                }
            }*/
    }

    /*public void loadKits(MySQL mySQL) {
        if (!mySQL.hasConnection()) {
            try {
                mySQL.openConnection();
                loadKits(mySQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {

            String query = "SELECT * FROM `" + DatabaseField.KIT_TABLE + "`;";
            ResultSet rs = mySQL.syncQuery(query);
            if (rs != null) {
                try {
                    if (rs.next()) {
                        do {
                            GameKit gameKit = FEPlugin.getGson().fromJson(rs.getString("data"), GameKit.class);
                            if (gameKit != null) {
                                if (kits.containsKey(gameKit.getId())) {
                                    Debugger.info("A kit is already loaded with the id '" + gameKit.getId() + "'");
                                } else {
                                    kits.put(gameKit.getId(), gameKit);
                                    kitsId.put(gameKit.getName(), gameKit.getId());
                                }
                            }
                        } while (rs.next());

                        *//**//*while (rs.next()) {
                            GameKit gameKit = FEPlugin.getGson().fromJson(rs.getString("data"), GameKit.class);
                            if (gameKit != null) {
                                if (kits.containsKey(gameKit.getId())) {
                                    LogUtils.severe("A kit is already loaded with the id '" + gameKit.getId() + "'");
                                } else {
                                    kits.put(gameKit.getId(), gameKit);
                                    kitsId.put(gameKit.getName(), gameKit.getId());
                                }
                            }
                        }*//**//*
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
    }*/

    public Map<Integer, GameKit> getKits() {
        return kits;
    }

    public Map<UUID, String> getPlayerKits() {
        return playerKits;
    }
}
