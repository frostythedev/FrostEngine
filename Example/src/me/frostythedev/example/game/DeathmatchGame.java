package me.frostythedev.example.game;


import me.frostythedev.example.game.executors.EndGame;
import me.frostythedev.example.game.executors.StartGame;
import me.frostythedev.example.game.utilities.TeamLoadUtility;
import me.frostythedev.frostengine.bukkit.gameapi.Minigame;
import me.frostythedev.frostengine.bukkit.gameapi.core.GameSettings;
import me.frostythedev.frostengine.bukkit.gameapi.teams.GameTeam;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DeathmatchGame extends Minigame {

    public DeathmatchGame() {
        super(ModuleExample.get(), "Deathmatch","Deathmatch", "description", "1.0.0", "frostythedev");
        setName("Deathmatch");
        setDisplayName("Deathmatch");
        setMinPlayers(1);
        setMaxPlayers(12);
    }

    @Override
    public void setup() {
        super.setup();

        this.withDefaultStates();

        this.addUtility(new TeamLoadUtility(this));

        GameSettings settings = new GameSettings();
        settings.setBuckets(false);
        settings.setBuild(false);
        settings.setDestroy(false);
        settings.setPvp(false);
        settings.setPve(false);
        settings.setInteract(true);
        settings.setChat(true);
        settings.setExplode(false);
        settings.setDrop(false);
        settings.setPickup(false);
        settings.setNotify(true);
        settings.setTeleport(true);

        setGameSettings(settings);

        this.switchState(0);

        this.setGameStartExecutor(new StartGame(this));
        this.setGameEndExecutor(new EndGame(this));

        this.setDefaultStartingCountdown();
    }

    @Override
    public void chat(Player player, String message) {
         System.out.println("Chat call in: " + getClass().getName());
        if (!getTeamManager().hasTeam(player)) {
            Locale.error(player, "&cYou are not apart of a team, please contact an administrator if the problem persist.");
        } else {
            GameTeam team = getTeamManager().getPlayerTeam(player);
            String format = team.getDisplayName() + "&r " + team.getNameColor() + player.getName() + "&r&7: " +
                    team.getChatColor() + message;

            team.teamChat(format);
        }
    }
}
