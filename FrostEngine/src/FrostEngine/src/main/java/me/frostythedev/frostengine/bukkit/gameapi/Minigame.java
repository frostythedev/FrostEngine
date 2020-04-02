package me.frostythedev.frostengine.bukkit.gameapi;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.frostythedev.frostengine.bukkit.gameapi.arenas.GameArena;
import me.frostythedev.frostengine.bukkit.gameapi.core.Game;
import me.frostythedev.frostengine.bukkit.gameapi.core.GameSettings;
import me.frostythedev.frostengine.bukkit.gameapi.core.utilities.GameUtility;
import me.frostythedev.frostengine.bukkit.gameapi.gamestate.GameState;
import me.frostythedev.frostengine.bukkit.gameapi.gamestate.StateAction;
import me.frostythedev.frostengine.bukkit.gameapi.gamestate.api.LobbyGameState;
import me.frostythedev.frostengine.bukkit.gameapi.gamestate.api.PreGameState;
import me.frostythedev.frostengine.bukkit.gameapi.kits.KitManager;
import me.frostythedev.frostengine.bukkit.gameapi.teams.GameTeamManager;
import me.frostythedev.frostengine.data.mysql.MySQL;
import me.frostythedev.frostengine.bukkit.gameapi.arenas.ArenaManager;
import me.frostythedev.frostengine.bukkit.gameapi.core.executors.GameEndExecutor;
import me.frostythedev.frostengine.bukkit.gameapi.core.executors.GameStartExecutor;
import me.frostythedev.frostengine.bukkit.gameapi.core.threads.GameCountdown;
import me.frostythedev.frostengine.bukkit.gameapi.gamestate.api.EndedGameState;
import me.frostythedev.frostengine.bukkit.gameapi.gamestate.api.InGameState;
import me.frostythedev.frostengine.bukkit.gameapi.listeners.GameListener;
import me.frostythedev.frostengine.bukkit.gameapi.listeners.KitListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Map;

public abstract class Minigame implements Game {

    private JavaPlugin plugin;

    private String name;
    private String displayName;
    private String description;
    private String version;
    private String author;

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

    public Minigame(JavaPlugin plugin, String name, String displayName, String description,
                    String version, String author) {
        this.plugin = plugin;
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.version = version;
        this.author = author;
    }

    public boolean switchState(int stateId) {
        if (getGameState(stateId) != null) {
            setGameState(getGameState(stateId));
            return true;
        }
        return false;
    }

    public void setDefaultStartingCountdown() {
        this.setStartingCountdown(new GameCountdown(this));
        this.getStartingCountdown().schedule();
    }

    public void onMinigameEnable() {
        setup();

        if (!utilities.isEmpty()) {
            utilities.forEach(GameUtility::start);
        }

        this.registerListener(new GameListener(this));
        this.registerListener(new KitListener(this));
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

        this.kitManager.loadKits();
        this.arenaManager.loadArenas();

        /*if (mySQL != null) {

            try {

            } catch (ArenaAlreadyLoadedException e) {
                e.printStackTrace();
            }
        }*/

        if (gameSettings == null) {
            gameSettings = new GameSettings();
        }

        Bukkit.getServer().getPluginManager().registerEvents(gameSettings, plugin);
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
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

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
        if(this.gameSettings != null){
            HandlerList.unregisterAll(this.gameSettings);
        }
        this.gameSettings = gameSettings;
        Bukkit.getServer().getPluginManager().registerEvents(gameSettings, plugin);

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
