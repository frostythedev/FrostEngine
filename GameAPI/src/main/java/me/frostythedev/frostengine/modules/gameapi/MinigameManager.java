package me.frostythedev.frostengine.modules.gameapi;

import com.google.inject.Singleton;
import me.frostythedev.frostengine.bukkit.utils.LogUtils;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class MinigameManager {

   private Map<String, Game> minigames;

    public MinigameManager() {
        this.minigames = new HashMap<>();
    }

    public Optional<Game> getMinigame(String mgName){
        if(minigames.containsKey(mgName)){
            return Optional.of(minigames.get(mgName));
        }
        return Optional.empty();
    }

    public void loadMinigame(Game minigame){
        if(getMinigame(minigame.getName()).isPresent()){
            LogUtils.info("Cannot load a minigame with that name as it is already loaded!");
            return;
        }

        LogUtils.info("Loaded minigame with name: " + minigame.getName());
        this.minigames.put(minigame.getName(), minigame);
    }

    public ArrayList<Game> getAll(){
        return new ArrayList<>(this.minigames.values());
    }


}
