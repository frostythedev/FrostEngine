package gameapi.exception;

import me.frostythedev.frostengine.bukkit.exceptions.IException;
import gameapi.gamestate.GameState;

public class GameStateRegisteredException extends IException{

    public GameStateRegisteredException(GameState gameState) {
        super("The gameState '" + gameState.getName() + "' with id '" + gameState.getId() + "' has already been registered.");
    }
}
