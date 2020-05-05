package me.frostythedev.frostengine.modules.gameapi.gamestate.defaults;

import me.frostythedev.frostengine.bukkit.messaging.Locale;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import me.frostythedev.frostengine.modules.gameapi.gamestate.GameState;
import me.frostythedev.frostengine.modules.gameapi.gamestate.core.IStateConstants;
import me.frostythedev.frostengine.modules.gameapi.gamestate.core.StateAction;
import me.frostythedev.frostengine.modules.gameapi.teams.GameTeam;
import org.bukkit.entity.Player;

import java.util.Collections;

public class LobbyGameState extends GameState {

    public LobbyGameState(Game game) {
        super(game, 0, IStateConstants.LOBBY_NAME, IStateConstants.LOBBY_DISPLAY, true);
    }

    @Override
    public void handle(Player player, StateAction action) {
        if (action.equals(StateAction.JOIN)) {
            if (!getGame().getTeamManager().hasTeam(player)) {
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
