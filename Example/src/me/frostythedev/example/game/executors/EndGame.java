package me.frostythedev.example.game.executors;

import me.frostythedev.example.game.DeathmatchGame;
import me.frostythedev.frostengine.bukkit.gameapi.core.executors.GameEndExecutor;

public class EndGame implements GameEndExecutor {

    private DeathmatchGame minigame;

    public EndGame(DeathmatchGame minigame) {
        this.minigame = minigame;
    }


    @Override
    public void endGame() {

    }
}
