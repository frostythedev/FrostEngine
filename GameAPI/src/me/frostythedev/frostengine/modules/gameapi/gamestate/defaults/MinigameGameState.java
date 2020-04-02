package gameapi.gamestate.defaults;

import gameapi.Minigame;
import gameapi.gamestate.GameState;

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
