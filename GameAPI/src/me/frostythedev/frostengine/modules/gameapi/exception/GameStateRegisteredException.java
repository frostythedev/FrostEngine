package me.frostythedev.frostengine.modules.gameapi.exception;

import me.frostythedev.frostengine.bukkit.exception.BaseException;
import me.frostythedev.frostengine.modules.gameapi.gamestate.GameState;

public class GameStateRegisteredException extends BaseException {

    public GameStateRegisteredException(GameState gameState) {
        super("The gameState '" + gameState.getName() + "' with id '" + gameState.getId() + "' has already been registered.");
    }
}
