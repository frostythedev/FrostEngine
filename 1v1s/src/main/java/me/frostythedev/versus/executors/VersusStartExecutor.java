package me.frostythedev.versus.executors;

import com.google.inject.Inject;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.modules.gameapi.arenas.GameArena;
import me.frostythedev.frostengine.modules.gameapi.core.executors.GameStartExecutor;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import me.frostythedev.versus.VersusGame;
import me.frostythedev.versus.VersusPlugin;
import org.bukkit.entity.Player;

public class VersusStartExecutor implements GameStartExecutor {

    @Inject
    VersusPlugin plugin;

    @Override
    public void startGame(Game game) {

        VersusGame versusGame = (VersusGame) game;

        Player p1 = versusGame.getPlayer1();
        Player p2 = versusGame.getPlayer2();

        GameArena gameArena = game.getArenaManager().getFreeArena();
        gameArena.setMinigame(game);

        game.getSettingManager().toggleOn("PVP");
        game.getSettingManager().toggleOn("Explode");

        game.getKitManager().setKit(p1, "VersusDefault");
        game.getKitManager().setKit(p2, "VersusDefault");
        game.getKitManager().giveKit(p1);
        game.getKitManager().giveKit(p2);

        Locale.message("&a&lLET THE GAMES BEGIN", p1,p2);
    }
}
