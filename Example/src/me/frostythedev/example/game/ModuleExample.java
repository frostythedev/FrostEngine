package me.frostythedev.example.game;

import org.bukkit.plugin.java.JavaPlugin;

public class ModuleExample extends JavaPlugin {


    private static ModuleExample inst;

    public static ModuleExample get() {
        return inst;
    }

    private DeathmatchGame game;

    @Override
    public void onEnable(){

        inst = this;
        this.game = new DeathmatchGame();
        this.game.onMinigameEnable();
    }

    public DeathmatchGame getGame() {
        return game;
    }
}
