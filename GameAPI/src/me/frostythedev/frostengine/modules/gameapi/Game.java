package gameapi.

import me.frostythedev.frostengine.bukkit.utilities.LogUtils;
import gameapi.gamestate.StateAction;
import gameapi.exception.GameStateRegisteredException;
import gameapi.gamestate.GameState;
import org.bukkit.entity.Player;

import java.util.Map;

public interface Game {

    default void setup() {
    }

    default void update() {
    }

    void handle(Player player, StateAction action);

    long tickDelay();

    Map<Integer, GameState> getGameStates();

    default void registerGameState(GameState gameState) {
        if (!getGameStates().containsKey(gameState.getId())) {
            getGameStates().put(gameState.getId(), gameState);

        } else {
            try {
                throw new GameStateRegisteredException(gameState);
            } catch (GameStateRegisteredException e) {
                LogUtils.severe(e.getErrorMessage(), e);
            }
        }
    }

    default void registerGameStates(GameState... states) {
        for (GameState state : states) {
            this.registerGameState(state);
        }
    }
}
