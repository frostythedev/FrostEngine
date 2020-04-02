package gameapi.gamestate.defaults;

import me.frostythedev.frostengine.bukkit.messaging.Locale;
import gameapi.Minigame;
import gameapi.gamestate.MinigameState;
import gameapi.gamestate.StateAction;
import gameapi.gamestate.StateConstants;
import gameapi.teams.GameTeam;
import gameapi.threads.EZCountdown;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;

public class PreGameState <T extends Minigame> extends MinigameState<T> {


    public PreGameState(T game) {
        super(game, 1, StateConstants.PREGAME_NAME, StateConstants.PREGAME_DISPLAY, true);
    }

    @Override
    public void onSwitch() {
        EZCountdown countdown = new EZCountdown(15, new int[]{5, 4, 3, 2, 1}) {
            public void onStart() {
                Bukkit.broadcastMessage(Locale.toColors("&7The game will begin in 15 seconds."));
            }

            public void onTick() {
            }

            public void onInterval() {
                Bukkit.broadcastMessage(Locale.toColors("&7The game will begin in " + getTicks() + " seconds."));
            }

            public void onEnd() {
                getMinigame().setGameState(getMinigame().getGameState(2));
            }
        };
        countdown.schedule();
    }

    @Override
    public void handle(Player player, StateAction action) {
        if (action.equals(StateAction.JOIN)) {
            if (!player.hasPermission("frostengine.game.pregame_join")) {
                player.kickPlayer("You do not have sufficient permission to join the game at this time.");
            } else {
                GameTeam smallest = getMinigame().getTeamManager().getSmallestTeam(Collections.singletonList("Spectator"));
                if (getMinigame().getTeamManager().setTeam(player, smallest)) {
                    player.sendMessage(Locale.toColors("&e>> You have joined the &b" + smallest.getName() + " &eteam!"));
                }
            }
        } else {
            getMinigame().getTeamManager().clearTeam(player);
        }
    }
}
