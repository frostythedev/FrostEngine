package gameapi.

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.module.Module;
import me.frostythedev.frostengine.bukkit.module.ModuleAPI;
import me.frostythedev.frostengine.data.mysql.MySQL;
import gameapi.arenas.GameArena;
import gameapi.exception.ArenaAlreadyLoadedException;
import gameapi.gamestate.GameState;
import gameapi.gamestate.StateAction;
import gameapi.gamestate.defaults.EndedGameState;
import gameapi.gamestate.defaults.InGameState;
import gameapi.kits.KitManager;
import gameapi.teams.GameTeamManager;
import gameapi.arenas.ArenaManager;
import gameapi.gamestate.defaults.LobbyGameState;
import gameapi.gamestate.defaults.PreGameState;
import gameapi.listeners.GameListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Map;

public abstract class Minigame extends Module implements Game {

    private String name;
    private String displayName;
    private long ticksDelay;
    private int minPlayers;
    private int maxPlayers;

    private GameCountdown startingCountdown;

    private GameState gameState;
    private Map<Integer, GameState> gameStates;

    private GameSettings gameSettings;
    private GameStartExecutor gameStartExecutor;
    private GameEndExecutor gameEndExecutor;

    private GameArena arena;

    private ArenaManager arenaManager;
    private GameTeamManager teamManager;
    private KitManager kitManager;

    private MySQL mySQL;

    private ArrayList<GameUtility> utilities;

    public Minigame(String moduleName, String description, String version, String author) {
        super(moduleName, description, version, author);
    }

    @Override
    public void onModuleEnable() {
        setup();

        if (!utilities.isEmpty()) {
            for (GameUtility utility : utilities) {
                utility.start();
            }
        }

        this.registerListener(new GameListener(this));

        if (ModuleAPI.getModule("GameAPI") != null) {
            setParent(ModuleAPI.getModule("GameAPI"));
        }
    }


    ///////////////////////////////////////////////
    // OVERRIDES
    ///////////////////////////////////////////////


    @Override
    public void setup() {
        this.gameStates = Maps.newHashMap();
        this.utilities = Lists.newArrayList();
        this.kitManager = new KitManager();
        this.teamManager = new GameTeamManager();
        this.arenaManager = new ArenaManager(this);

        if (mySQL != null) {
            this.kitManager.loadKits(mySQL);
            try {
                this.arenaManager.loadArenas(mySQL);
            } catch (ArenaAlreadyLoadedException e) {
                e.printStackTrace();
            }
        }

        if (gameSettings != null) {
            Bukkit.getServer().getPluginManager().registerEvents(gameSettings, FEPlugin.get());
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void handle(Player player, StateAction action) {
        getGameState().handle(player, action);
    }

    @Override
    public Map<Integer, GameState> getGameStates() {
        return gameStates;
    }

    @Override
    public long tickDelay() {
        return ticksDelay;
    }


    ///////////////////////////////////////////////
    // MADE METHODS
    ///////////////////////////////////////////////

    public void addUtility(GameUtility util) {
        if (!utilities.contains(util)) {
            utilities.add(util);
        }
    }

    public void withDefaultStates() {
        this.registerGameStates(
                new LobbyGameState<>(this),
                new PreGameState<>(this),
                new InGameState<>(this),
                new EndedGameState<>(this));
    }

    public GameState getGameState(int id) {
        if (gameStates.containsKey(id)) {
            return gameStates.get(id);
        }
        return null;
    }

    public void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, FEPlugin.get());
        }
    }

    public void registerListener(Listener listener) {
        registerListeners(listener);
    }


    public void chat(Player player, String message) {
    }

    public void killPlayer(Player player) {
    }


    ///////////////////////////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////////////////////////


    public GameCountdown getStartingCountdown() {
        return startingCountdown;
    }

    public void setStartingCountdown(GameCountdown startingCountdown) {
        this.startingCountdown = startingCountdown;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public long getTicksDelay() {
        return ticksDelay;
    }

    public void setTicksDelay(long ticksDelay) {
        this.ticksDelay = ticksDelay;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        gameState.onSwitch();
    }

    public void setGameStates(Map<Integer, GameState> gameStates) {
        this.gameStates = gameStates;
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public void setGameSettings(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    public GameTeamManager getTeamManager() {
        return teamManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    public void setMySQL(MySQL mySQL) {
        this.mySQL = mySQL;
    }

    public GameStartExecutor getGameStartExecutor() {
        return gameStartExecutor;
    }

    public void setGameStartExecutor(GameStartExecutor gameStartExecutor) {
        this.gameStartExecutor = gameStartExecutor;
    }

    public GameEndExecutor getGameEndExecutor() {
        return gameEndExecutor;
    }

    public void setGameEndExecutor(GameEndExecutor gameEndExecutor) {
        this.gameEndExecutor = gameEndExecutor;
    }

    public GameArena getArena() {
        return arena;
    }

    public void setArena(GameArena arena) {
        this.arena = arena;
    }
}
