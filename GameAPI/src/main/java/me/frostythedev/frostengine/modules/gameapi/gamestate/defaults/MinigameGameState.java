package me.frostythedev.frostengine.modules.gameapi.gamestate.defaults;

import me.frostythedev.frostengine.modules.gameapi.Minigame;
import me.frostythedev.frostengine.modules.gameapi.gamestate.GameState;

public class MinigameGameState<T extends Minigame> extends GameState {

    private T minigame;

    public MinigameGameState(int id, String name, String displayName, boolean joinable, T minigame) {
        super(id, name, displayName, joinable);
        this.minigame = minigame;
    }

    public T getMinigame() {
        return minigame;
    }
}
