package me.frostythedev.frostengine.bukkit.gameapi.gamestate.api;

import me.frostythedev.frostengine.bukkit.gameapi.Minigame;
import me.frostythedev.frostengine.bukkit.gameapi.gamestate.MinigameState;
import me.frostythedev.frostengine.bukkit.gameapi.gamestate.StateAction;
import me.frostythedev.frostengine.bukkit.gameapi.gamestate.IStateConstants;
import me.frostythedev.frostengine.bukkit.gameapi.teams.GameTeam;
import me.frostythedev.frostengine.bukkit.gameapi.threads.EZCountdown;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;

public class PreGameState <T extends Minigame> extends MinigameState<T> {


    public PreGameState(T game) {
        super(game, 1, IStateConstants.PREGAME_NAME, IStateConstants.PREGAME_DISPLAY, true);
    }

    @Override
    public void onSwitch() {
        EZCountdown countdown = new EZCountdown(15, new int[]{5, 4, 3, 2, 1}) {
            public void onStart() {
                Bukkit.broadcastMessage(Locale.toColors("&7The game will begin in 15 seconds."));
            }

            public void onTick() {
                if(Bukkit.getOnlinePlayers().size() < getMinigame().getMinPlayers()){
                    setTicks(15);
                }
            }

            public void onInterval() {
                Bukkit.broadcastMessage(Locale.toColors("&7The game will begin in " + getTicks() + " seconds."));
            }

            public void onEnd() {
                getMinigame().switchState(2);
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
