package me.frostythedev.frostengine.modules.gameapi.core.interfaces;

import me.frostythedev.frostengine.bukkit.utils.LogUtils;
import me.frostythedev.frostengine.data.mysql.MySQL;
import me.frostythedev.frostengine.modules.gameapi.arenas.ArenaManager;
import me.frostythedev.frostengine.modules.gameapi.arenas.GameArena;
import me.frostythedev.frostengine.modules.gameapi.core.executors.GameEndExecutor;
import me.frostythedev.frostengine.modules.gameapi.core.executors.GameStartExecutor;
import me.frostythedev.frostengine.modules.gameapi.core.gui.GUIGameSettings2;
import me.frostythedev.frostengine.modules.gameapi.core.settings.SettingManager;
import me.frostythedev.frostengine.modules.gameapi.core.threads.GameCountdown;
import me.frostythedev.frostengine.modules.gameapi.core.utilities.GameUtility;
import me.frostythedev.frostengine.modules.gameapi.exception.GameStateRegisteredException;
import me.frostythedev.frostengine.modules.gameapi.gamestate.GameState;
import me.frostythedev.frostengine.modules.gameapi.gamestate.core.StateAction;
import me.frostythedev.frostengine.modules.gameapi.gamestate.defaults.EndedGameState;
import me.frostythedev.frostengine.modules.gameapi.gamestate.defaults.InGameState;
import me.frostythedev.frostengine.modules.gameapi.gamestate.defaults.LobbyGameState;
import me.frostythedev.frostengine.modules.gameapi.gamestate.defaults.PreGameState;
import me.frostythedev.frostengine.modules.gameapi.kits.KitManager;
import me.frostythedev.frostengine.modules.gameapi.teams.GameTeamManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;

public interface Game {

    int getGameId();

    String getName();
    String getDisplayName();
    String getDescription();
    String getVersion();
    String getAuthor();

    long getTicksDelay();
    int getMinPlayers();
    int getMaxPlayers();

    @Deprecated
    GameArena getGameArena();

    GameCountdown getStartingCountdown();

    GameState getGameState();
    Map<Integer, GameState> getGameStates();

    GameStartExecutor getGameStartExecutor();
    GameEndExecutor getGameEndExecutor();
    GUIGameSettings2 getSettings();

    ArenaManager getArenaManager();
    GameTeamManager getTeamManager();
    KitManager getKitManager();
    SettingManager getSettingManager();

    MySQL getMySQL();

    ArrayList<GameUtility> getUtilities();
    long tickDelay();

    void handle(Player player, StateAction action);
    void loadManagers();
    void setGameState(GameState state);
    void killPlayer(Player player);
    void setArena(GameArena arena);

    default void setup() {
    }

    default void update() {
    }

    default void registerGameState(GameState gameState) {
        if (!getGameStates().containsKey(gameState.getId())) {
            getGameStates().put(gameState.getId(), gameState);

        } else {
            try {
                throw new GameStateRegisteredException(gameState);
            } catch (GameStateRegisteredException e) {
                LogUtils.log(Level.SEVERE, e.getErrorMessage());
            }
        }
    }

    default void registerGameStates(GameState... states) {
        for (GameState state : states) {
            this.registerGameState(state);
        }
    }

    default GameState getGameState(int id) {
        if (getGameStates().containsKey(id)) {
            return getGameStates().get(id);
        }
        return null;
    }

    default boolean switchState(int stateId) {
        if (getGameState(stateId) != null) {
            setGameState(getGameState(stateId));
            return true;
        }
        return false;
    }

    default void withDefaultStates() {
        this.registerGameStates(
                new LobbyGameState(this),
                new PreGameState(this),
                new InGameState(this),
                new EndedGameState(this));
    }
}
