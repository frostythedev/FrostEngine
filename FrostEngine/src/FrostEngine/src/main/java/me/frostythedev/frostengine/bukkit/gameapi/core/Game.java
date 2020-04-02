package me.frostythedev.frostengine.bukkit.gameapi.core;

import me.frostythedev.frostengine.bukkit.debug.Debugger;
import me.frostythedev.frostengine.bukkit.gameapi.exception.GameStateRegisteredException;
import me.frostythedev.frostengine.bukkit.gameapi.gamestate.GameState;
import me.frostythedev.frostengine.bukkit.gameapi.gamestate.StateAction;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.logging.Level;

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
                Debugger.log(Level.SEVERE, "Error", e);
            }
        }
    }

    default void registerGameStates(GameState... states) {
        for (GameState state : states) {
            this.registerGameState(state);
        }
    }
}
