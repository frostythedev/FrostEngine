package me.frostythedev.versus.executors;

import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.modules.gameapi.arenas.GameArena;
import me.frostythedev.frostengine.modules.gameapi.core.executors.GameStartExecutor;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import org.bukkit.entity.Player;

public class VersusStartExecutor implements GameStartExecutor {

    @Override
    public void startGame(Game game) {

        Player p1 = game.getTeamManager().getPlayersOfTeam("Players").get(0);
        Player p2 = game.getTeamManager().getPlayersOfTeam("Players").get(1);

        GameArena gameArena = game.getArenaManager().getFreeArena();
        p1.teleport(gameArena.getSpawn(0));
        p2.teleport(gameArena.getSpawn(1));

        game.getSettingManager().toggleOn("PVP");
        game.getSettingManager().toggleOn("Explode");

        game.getKitManager().setKit(p1, "VersusDefault");
        game.getKitManager().setKit(p2, "VersusDefault");
        game.getKitManager().giveAllKits();

        Locale.message(p1, "&a&lLET THE GAMES BEGIN");
        Locale.message(p2, "&a&lLET THE GAMES BEGIN");


    }
}
