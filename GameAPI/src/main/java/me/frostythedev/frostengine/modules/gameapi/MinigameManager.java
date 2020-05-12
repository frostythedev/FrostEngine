package me.frostythedev.frostengine.modules.gameapi;

import com.google.inject.Singleton;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import org.bukkit.entity.Player;

import java.util.*;

@Singleton
public class MinigameManager implements MGManager {

   private Map<String, Game> minigames;
   private Map<UUID, String> playerGames;

    public MinigameManager() {
        this.minigames = new HashMap<>();
        this.playerGames = new HashMap<>();
    }

    public boolean addPlayerToGame(Player player, Game game){
        if(!getMinigame(game.getName()).isPresent()) return false;

        if(getGameFromPlayer(player).isPresent()){
            getGameFromPlayer(player).get().removePlayer(player);
            playerGames.remove(player.getUniqueId());
        }

        if(game.addPlayer(player)){
            playerGames.put(player.getUniqueId(), game.getName());
            return true;
        }

        return false;
    }

    public void clearGameFromUUID(UUID uuid){
        playerGames.remove(uuid);
    }

    public void clearGameFromPlayer(Player player){
        getGameFromPlayer(player).ifPresent(game -> {
            if(game.removePlayer(player)){
                clearGameFromUUID(player.getUniqueId());
            }
        });
    }

    public boolean registerGame(Game game){
        if(getGame(game.getName()).isPresent()) return false;

        return this.minigames.put(game.getName(), game) != null;
    }

    public boolean unregisterGame(Game game){
        if(!getGame(game.getName()).isPresent()) return false;

        return this.minigames.remove(game.getName(), game);
    }

    public Optional<Game> getGameFromPlayer(Player player){
        if(!playerGames.containsKey(player.getUniqueId())) return Optional.empty();

        return getGame(playerGames.get(player.getUniqueId()));
    }

    public Optional<Game> getGame(String gameName){
        if(this.minigames.containsKey(gameName)){
            return Optional.of(this.minigames.get(gameName));
        }

        return Optional.empty();
    }

    @Override
    public Map<String, Game> getMinigames() {
        return minigames;
    }

    public Map<UUID, String> getPlayerGames() {
        return playerGames;
    }

    public ArrayList<Game> getAll(){
        return new ArrayList<>(this.minigames.values());
    }


}
