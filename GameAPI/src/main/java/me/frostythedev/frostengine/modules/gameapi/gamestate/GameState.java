package me.frostythedev.frostengine.modules.gameapi.gamestate;

import me.frostythedev.frostengine.modules.gameapi.gamestate.core.StateAction;
import org.bukkit.entity.Player;

public abstract class GameState {

    private int id;
    private String name;
    private String displayName;
    private boolean joinable;

    public GameState(int id, String name, String displayName, boolean joinable) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.joinable = joinable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isJoinable() {
        return joinable;
    }

    public void setJoinable(boolean joinable) {
        this.joinable = joinable;
    }

    public int getNextState(){ return 0;}

    public void onSwitch() {}

    public void handle(Player player, StateAction action) {}
}
