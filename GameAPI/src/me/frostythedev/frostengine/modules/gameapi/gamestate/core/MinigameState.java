package me.frostythedev.frostengine.modules.gameapi.gamestate.core;

import me.frostythedev.frostengine.modules.gameapi.Minigame;
import me.frostythedev.frostengine.modules.gameapi.gamestate.GameState;

public class MinigameState<T extends Minigame> extends GameState {

    private T minigame;

    public MinigameState(T minigame, int id, String name, String displayName, boolean joinable) {
        super(id, name, displayName, joinable);
        this.minigame = minigame;
    }

    public T getMinigame() {
        return minigame;
    }
}
