package me.frostythedev.frostengine.modules.gameapi;

import com.google.inject.Singleton;
import me.frostythedev.frostengine.bukkit.utils.LogUtils;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class ArenaMGManager implements MGManager {

    private Map<String, Game> arenaGames;
   // private Map<Integer, >

    public ArenaMGManager() {
        this.arenaGames = new HashMap<>();
    }

    public void startGame(ArenaBaseMinigame arenaMg){

    }

    @Override
    public void loadMinigame(Game minigame) {
        if(minigame instanceof Minigame) return;

        ArenaBaseMinigame arenaMG = (ArenaBaseMinigame) minigame;
        int size = getMinigames().size();
        if(getMinigame(minigame.getName()).isPresent()){
            LogUtils.info("Cannot load a minigame with that name as it is already loaded!");
            return;
        }

        LogUtils.info("Loaded minigame with name: " + minigame.getName());
        this.getMinigames().put(minigame.getName(), minigame);
    }

    @Override
    public Map<String, Game> getMinigames() {
        return arenaGames;
    }
}
