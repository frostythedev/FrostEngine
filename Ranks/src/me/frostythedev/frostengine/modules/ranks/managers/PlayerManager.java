package me.frostythedev.frostengine.modules.ranks.managers;

import com.google.common.collect.Maps;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.data.core.Database;
import me.frostythedev.frostengine.modules.ranks.callback.RankPlayerCallback;
import me.frostythedev.frostengine.modules.ranks.objects.RankPlayer;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    private Map<UUID, RankPlayer> players;

    public PlayerManager() {
        players = Maps.newHashMap();
    }

    public RankPlayer loadPlayer(Player player){
        if(players.containsKey(player.getUniqueId())){
            return players.get(player.getUniqueId());
        }else{

            RankPlayerCallback rankPlayerCallback = new RankPlayerCallback();
            Database.get().syncQuery("SELECT * FROM fe_players WHERE uuid=?", new Object[]{player.getUniqueId().toString()}, rankPlayerCallback);

            if(rankPlayerCallback.result() != null){
                RankPlayer rankPlayer = (RankPlayer) rankPlayerCallback.result();
                this.players.put(player.getUniqueId(), rankPlayer);
                return rankPlayer;
            }else{
               RankPlayer rankPlayer = new RankPlayer(player.getUniqueId());
                createPlayer(rankPlayer);
                return loadPlayer(player);
            }
        }
    }

    public void savePlayer(RankPlayer player){
        String playerData = FEPlugin.getGson().toJson(player);
        Database.get().syncUpdate("UPDATE fe_players SET data=? WHERE uuid=?", new Object[]{playerData,player.getUuid().toString()});
    }

    public void createPlayer(RankPlayer player){
        String playerData = FEPlugin.getGson().toJson(player);
        String uuid = player.getUuid().toString();
        Database.get().syncUpdate("INSERT INTO fe_players(uuid,data) VALUES (?,?)", new Object[]{player.getUuid().toString(), playerData});

    }

    public void saveAllPlayers(){
        players.values().forEach(this::savePlayer);
    }

    public RankPlayer getPlayer(Player player){
        UUID uuid = player.getUniqueId();
        if(players.containsKey(uuid)){
            return players.get(uuid);
        }
        return null;
    }

    public boolean isLoaded(Player player){
        return getPlayer(player) != null;
    }
}
