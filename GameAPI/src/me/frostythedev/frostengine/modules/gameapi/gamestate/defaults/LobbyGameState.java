package gameapi.gamestate.defaults;

import me.frostythedev.frostengine.bukkit.messaging.Locale;
import gameapi.Minigame;
import gameapi.gamestate.MinigameState;
import gameapi.gamestate.StateAction;
import gameapi.gamestate.StateConstants;
import gameapi.teams.GameTeam;
import org.bukkit.entity.Player;

import java.util.Collections;

public class LobbyGameState<T extends Minigame> extends MinigameState<T> {

    public LobbyGameState(T game) {
        super(game, 0, StateConstants.LOBBY_NAME, StateConstants.LOBBY_DISPLAY, true);
    }

    @Override
    public void handle(Player player, StateAction action) {
        if (action.equals(StateAction.JOIN)) {
            if (!getMinigame().getTeamManager().hasTeam(player)) {
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
