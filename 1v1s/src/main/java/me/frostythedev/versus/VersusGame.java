package me.frostythedev.versus;

import me.frostythedev.frostengine.modules.gameapi.ArenaBaseMinigame;
import me.frostythedev.versus.executors.VersusStartExecutor;

public class VersusGame extends ArenaBaseMinigame {

    public VersusGame() {
        super("1v1", "Players fight one another to the death", 2, 2);
    }

    @Override
    public void setup() {

        this.setGameStartExecutor(new VersusStartExecutor());
    }
}
