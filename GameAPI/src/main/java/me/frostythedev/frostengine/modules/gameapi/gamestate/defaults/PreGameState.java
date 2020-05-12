package me.frostythedev.frostengine.modules.gameapi.gamestate.defaults;

import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.modules.gameapi.arenas.GameArena;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import me.frostythedev.frostengine.modules.gameapi.gamestate.GameState;
import me.frostythedev.frostengine.modules.gameapi.gamestate.core.IStateConstants;
import me.frostythedev.frostengine.modules.gameapi.gamestate.core.StateAction;
import me.frostythedev.frostengine.modules.gameapi.teams.GameTeam;
import me.frostythedev.frostengine.modules.gameapi.threads.EZCountdown;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;

public class PreGameState extends GameState {


    public PreGameState(Game game) {
        super(game, 1, IStateConstants.PREGAME_NAME, IStateConstants.PREGAME_DISPLAY, true);
    }

    @Override
    public void onSwitch() {
        GameArena arena = getGame().getGameArena();

        for(int i = 0; i < getGame().getTeamManager().getAllPlayers().get().size(); i++){
            Bukkit.getPlayer(getGame().getTeamManager().getAllPlayers().get().get(i)).teleport(arena.getSpawn(i));
        }
        getGame().getSettingManager().toggleOff("Movement");

        EZCountdown countdown = new EZCountdown(15, new int[]{5, 4, 3, 2, 1}) {
            public void onStart() {
                getGame().broadcast(Locale.toColors("&7The game will begin in 15 seconds."));
            }

            public void onTick() {
                if(Bukkit.getOnlinePlayers().size() < getGame().getMinPlayers()){
                    setTicks(15);
                }
            }

            public void onInterval() {
                getGame().broadcast(Locale.toColors("&7The game will begin in " + getTicks() + " seconds."));
            }

            public void onEnd() {
                getGame().switchState(2);
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
                GameTeam smallest = getGame().getTeamManager().getSmallestTeam(Collections.singletonList("Spectator"));
                if (getGame().getTeamManager().setTeam(player, smallest)) {
                    player.sendMessage(Locale.toColors("&e>> You have joined the &b" + smallest.getName() + " &eteam!"));
                }
            }
        } else {
            getGame().getTeamManager().clearTeam(player);
        }
    }
}
