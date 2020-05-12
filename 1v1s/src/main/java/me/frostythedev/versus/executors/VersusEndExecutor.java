package me.frostythedev.versus.executors;

import com.google.inject.Inject;
import me.frostythedev.frostengine.modules.gameapi.MinigameManager;
import me.frostythedev.frostengine.modules.gameapi.core.executors.GameEndExecutor;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import me.frostythedev.versus.VersusGame;

public class VersusEndExecutor implements GameEndExecutor {

    @Inject
    MinigameManager minigameManager;

    @Override
    public void endGame(Game game) {

        VersusGame versusGame = (VersusGame) game;

        if(versusGame.getGameArena() != null){
            versusGame.getGameArena().setMinigame(null);
        }

        versusGame.getPlayer1().getInventory().clear();
        versusGame.getPlayer2().getInventory().clear();

        minigameManager.clearGameFromPlayer(versusGame.getPlayer1());
        minigameManager.clearGameFromPlayer(versusGame.getPlayer2());


        if(versusGame.getArenaManager().getLobbyArena() != null){
            versusGame.getPlayer1().teleport(versusGame.getArenaManager().getLobbyArena().getSpawn(0));
            versusGame.getPlayer2().teleport(versusGame.getArenaManager().getLobbyArena().getSpawn(0));
        }

        versusGame.setPlayer1(null);
        versusGame.setPlayer2(null);

        minigameManager.unregisterGame(game);
        //versusGame.switchState(0);
    }
}
