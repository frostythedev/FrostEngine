package me.frostythedev.frostengine.modules.gameapi;

import me.frostythedev.frostengine.bukkit.debug.Debugger;
import me.frostythedev.frostengine.bukkit.utils.LogUtils;

import java.util.*;

public class MinigameManager {

   private Map<String, Minigame> minigames;

    public MinigameManager() {
        this.minigames = new HashMap<>();
    }

    public Optional<Minigame> getMinigame(String mgName){
        if(minigames.containsKey(mgName)){
            return Optional.of(minigames.get(mgName));
        }
        return Optional.empty();
    }

    public void loadMinigame(Minigame minigame){
        if(getMinigame(minigame.getName()).isPresent()){
            Debugger.info("Cannot load a minigame with that name as it is already loaded!");
            return;
        }

        LogUtils.info("Loaded minigame with name: " + minigame.getName());
        this.minigames.put(minigame.getName(), minigame);
    }

    public ArrayList<Minigame> getAll(){
        return new ArrayList<>(this.minigames.values());
    }


}
