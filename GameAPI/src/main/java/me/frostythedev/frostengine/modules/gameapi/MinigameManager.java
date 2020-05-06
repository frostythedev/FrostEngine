package me.frostythedev.frostengine.modules.gameapi;

import com.google.inject.Singleton;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class MinigameManager implements MGManager {

   private Map<String, Game> minigames;

    public MinigameManager() {
        this.minigames = new HashMap<>();
    }

    @Override
    public Map<String, Game> getMinigames() {
        return minigames;
    }

    public ArrayList<Game> getAll(){
        return new ArrayList<>(this.minigames.values());
    }


}
