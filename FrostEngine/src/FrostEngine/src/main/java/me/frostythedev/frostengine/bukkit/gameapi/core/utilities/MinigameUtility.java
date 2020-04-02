package me.frostythedev.frostengine.bukkit.gameapi.core.utilities;

import me.frostythedev.frostengine.bukkit.gameapi.Minigame;

public class MinigameUtility<T extends Minigame> implements GameUtility<T> {

    private T minigame;

    public MinigameUtility(T minigame) {
        this.minigame = minigame;
    }

    @Override
    public T getMinigame() {
        return minigame;
    }
}
