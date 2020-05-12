package me.frostythedev.frostengine.modules.gameapi;

import com.google.inject.Inject;
import me.frostythedev.frostengine.data.mysql.MySQL;
import me.frostythedev.frostengine.modules.gameapi.arenas.ArenaManager;
import me.frostythedev.frostengine.modules.gameapi.arenas.GameArena;
import me.frostythedev.frostengine.modules.gameapi.core.executors.GameEndExecutor;
import me.frostythedev.frostengine.modules.gameapi.core.executors.GameStartExecutor;
import me.frostythedev.frostengine.modules.gameapi.core.gui.GUIGameSettings2;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import me.frostythedev.frostengine.modules.gameapi.core.settings.SettingManager;
import me.frostythedev.frostengine.modules.gameapi.core.threads.LobbyCountdown;
import me.frostythedev.frostengine.modules.gameapi.core.utilities.GameUtility;
import me.frostythedev.frostengine.modules.gameapi.gamestate.GameState;
import me.frostythedev.frostengine.modules.gameapi.gamestate.core.StateAction;
import me.frostythedev.frostengine.modules.gameapi.kits.KitManager;
import me.frostythedev.frostengine.modules.gameapi.teams.GameTeamManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Map;

public class ArenaBaseMinigame implements Game {

    @Inject
    GameAPI gameAPI;

    private int gameId;

    private String name;
    private String displayName;
    private String description;
    private String version;

    private int minPlayers, maxPlayers;
    private long tickDelay;
    private boolean teamChat;

    private LobbyCountdown startingCountdown;

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

    public ArenaBaseMinigame(String displayName, String description, int minPlayers, int maxPlayers) {
        this.displayName = displayName;
        this.description = description;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }




    ///////////////////////////////////////////////
    // OVERRIDES
    ///////////////////////////////////////////////

    @Override
    public void setup() {

    }

    @Override
    public void broadcast(String message) {

    }

    @Override
    public boolean addPlayer(Player player) {
        return false;
    }

    @Override
    public boolean removePlayer(Player player) {
        return false;
    }

    @Override
    public void registerListeners(Listener... listeners) {
        gameAPI.registerListeners(listeners);
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

    @Override
    public void killPlayer(Player player) {

    }


    ///////////////////////////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////////////////////////


    @Override
    public int getGameId() {
        return this.gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
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
    public boolean isTeamChat() {
        return teamChat;
    }

    @Override
    public GameArena getGameArena() {
        return gameArena;
    }

    @Override
    public LobbyCountdown getStartingCountdown() {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setTickDelay(long tickDelay) {
        this.tickDelay = tickDelay;
    }

    public void setStartingCountdown(LobbyCountdown startingCountdown) {
        this.startingCountdown = startingCountdown;
    }

    public void setGameStates(Map<Integer, GameState> gameStates) {
        this.gameStates = gameStates;
    }

    public void setGameStartExecutor(GameStartExecutor gameStartExecutor) {
        this.gameStartExecutor = gameStartExecutor;
    }

    public void setGameEndExecutor(GameEndExecutor gameEndExecutor) {
        this.gameEndExecutor = gameEndExecutor;
    }

    public void setSettingsGUI(GUIGameSettings2 settingsGUI) {
        this.settingsGUI = settingsGUI;
    }

    public void setGameArena(GameArena gameArena) {
        this.gameArena = gameArena;
    }

    public void setArenaManager(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    public void setTeamManager(GameTeamManager teamManager) {
        this.teamManager = teamManager;
    }

    public void setKitManager(KitManager kitManager) {
        this.kitManager = kitManager;
    }

    public void setSettingManager(SettingManager settingManager) {
        this.settingManager = settingManager;
    }

    public void setMySQL(MySQL mySQL) {
        this.mySQL = mySQL;
    }

    public void setUtilities(ArrayList<GameUtility> utilities) {
        this.utilities = utilities;
    }
}
