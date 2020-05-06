package me.frostythedev.frostengine.modules.gameapi;

import me.frostythedev.frostengine.bukkit.utils.LogUtils;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;

import java.util.Map;
import java.util.Optional;

public interface MGManager {

    Map<String, Game> getMinigames();

    default Optional<Game> getMinigame(String mgName){
        if(getMinigames().containsKey(mgName)){
            return Optional.of(getMinigames().get(mgName));
        }
        return Optional.empty();
    }

    default void loadMinigame(Game minigame){
        if(getMinigame(minigame.getName()).isPresent()){
            LogUtils.info("Cannot load a minigame with that name as it is already loaded!");
            return;
        }

        LogUtils.info("Loaded minigame with name: " + minigame.getName());
        this.getMinigames().put(minigame.getName(), minigame);
    }
}
