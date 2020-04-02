package me.frostythedev.frostengine.bukkit.gameapi.gamestate;

import me.frostythedev.frostengine.bukkit.gameapi.Minigame;

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
