package me.frostythedev.frostengine.modules.gameapi;

import me.frostythedev.frostengine.data.mysql.MySQL;
import me.frostythedev.frostengine.modules.gameapi.arenas.ArenaManager;
import me.frostythedev.frostengine.modules.gameapi.arenas.GameArena;
import me.frostythedev.frostengine.modules.gameapi.core.executors.GameEndExecutor;
import me.frostythedev.frostengine.modules.gameapi.core.executors.GameStartExecutor;
import me.frostythedev.frostengine.modules.gameapi.core.gui.GUIGameSettings2;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import me.frostythedev.frostengine.modules.gameapi.core.settings.SettingManager;
import me.frostythedev.frostengine.modules.gameapi.core.threads.GameCountdown;
import me.frostythedev.frostengine.modules.gameapi.core.utilities.GameUtility;
import me.frostythedev.frostengine.modules.gameapi.gamestate.GameState;
import me.frostythedev.frostengine.modules.gameapi.gamestate.core.StateAction;
import me.frostythedev.frostengine.modules.gameapi.kits.KitManager;
import me.frostythedev.frostengine.modules.gameapi.teams.GameTeamManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Map;

public class ArenaMinigame implements Game {

    private int gameId;

    private String name;
    private String displayName;
    private String description;
    private String version;

    private int minPlayers, maxPlayers;
    private long tickDelay;

    private GameCountdown startingCountdown;

    private GameState gameState;
    private Map<Integer, GameState> gameStates;

    private GameStartExecutor gameStartExecutor;
    private GameEndExecutor gameEndExecutor;
    private GUIGameSettings2 settingsGUI;

    private GameArena gameArena;

    private ArenaManager arenaManager;
    private GameTeamManager teamManager;
    private KitManager kitManager;
    private SettingManager settingManager;

    private MySQL mySQL;

    private ArrayList<GameUtility> utilities;

    public ArenaMinigame(String displayName, String description, int minPlayers, int maxPlayers) {
        this.displayName = displayName;
        this.description = description;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getAuthor() {
        return null;
    }

    @Override
    public long getTicksDelay() {
        return tickDelay;
    }

    @Override
    public int getMinPlayers() {
        return minPlayers;
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public GameArena getGameArena() {
        return gameArena;
    }

    @Override
    public GameCountdown getStartingCountdown() {
        return startingCountdown;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public Map<Integer, GameState> getGameStates() {
        return gameStates;
    }

    @Override
    public GameStartExecutor getGameStartExecutor() {
        return gameStartExecutor;
    }

    @Override
    public GameEndExecutor getGameEndExecutor() {
        return gameEndExecutor;
    }

    @Override
    public GUIGameSettings2 getSettings() {
        return settingsGUI;
    }

    @Override
    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    @Override
    public GameTeamManager getTeamManager() {
        return teamManager;
    }

    @Override
    public KitManager getKitManager() {
        return kitManager;
    }

    @Override
    public SettingManager getSettingManager() {
        return settingManager;
    }

    @Override
    public MySQL getMySQL() {
        return mySQL;
    }

    @Override
    public ArrayList<GameUtility> getUtilities() {
        return utilities;
    }

    @Override
    public long tickDelay() {
        return 0;
    }

    @Override
    public void handle(Player player, StateAction action) {

    }

    @Override
    public void loadManagers() {

    }

    @Override
    public void setGameState(GameState state) {

    }
}
