package me.frostythedev.example.game;


import me.frostythedev.example.game.executors.EndGame;
import me.frostythedev.example.game.executors.StartGame;
import me.frostythedev.example.game.utilities.TeamLoadUtility;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.modules.gameapi.Minigame;
import me.frostythedev.frostengine.modules.gameapi.arenas.GameArena;
import me.frostythedev.frostengine.modules.gameapi.teams.GameTeam;
import org.bukkit.entity.Player;

public class DeathmatchGame extends Minigame {

    //MEANT TO BE AN EXAMPLE OF A SERVER WIDE MINIGAME

    public DeathmatchGame() {
        super(FEPlugin.get(), "Deathmatch","Deathmatch", "description", "1.0.0", "frostythedev");
        setName("Deathmatch");
        setDisplayName("Deathmatch");
        setMinPlayers(2);
        setMaxPlayers(12);
    }

    @Override
    public GameArena getGameArena() {
        return null;
    }

    @Override
    public void setup() {
        super.setup();

        //this.withDefaultStates();
        this.withDefaultStates();
        /*this.registerGameStates(
                new LobbyState(this),
                new PreGameState<>(this),
                new InGameState<>(this),
                new EndedGameState<>(this));*/

        this.addUtility(new TeamLoadUtility(this));

        this.getSettingManager().toggleOn("Chat");
        this.getSettingManager().toggleOn("Movement");
        this.getSettingManager().toggleOn("Notify");

        /*GameSettings settings = new GameSettings();
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
        settings.setMovement(true);

        setGameSettings(settings);*/

        this.switchState(0);

        this.setGameStartExecutor(new StartGame());
        this.setGameEndExecutor(new EndGame());

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
