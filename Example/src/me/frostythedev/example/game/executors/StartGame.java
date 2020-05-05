package me.frostythedev.example.game.executors;

import me.frostythedev.example.game.DeathmatchGame;
import me.frostythedev.frostengine.modules.gameapi.arenas.GameArena;
import me.frostythedev.frostengine.modules.gameapi.core.GameSettings;
import me.frostythedev.frostengine.modules.gameapi.core.executors.GameStartExecutor;
import org.bukkit.Bukkit;

public class StartGame implements GameStartExecutor {

    private DeathmatchGame minigame;

    public StartGame(DeathmatchGame minigame) {
        this.minigame = minigame;
    }

    @Override
    public void startGame() {
        //NO LONGER USED, SYSTEM HAS BEEN UPDATED TO UTILIZE THE SETTING MANAGER
/*        GameSettings settings = minigame.getGameSettings();
        settings.setPvp(true);
        settings.setPve(true);*/

        minigame.getSettingManager().toggleOn("PVP");
        minigame.getSettingManager().toggleOn("PVE");

        Bukkit.broadcastMessage("THE GAME HAS BEGAN");
    }
}
