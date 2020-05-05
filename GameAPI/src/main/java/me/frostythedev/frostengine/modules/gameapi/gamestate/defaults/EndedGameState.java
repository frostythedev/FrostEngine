package me.frostythedev.frostengine.modules.gameapi.gamestate.defaults;

import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import me.frostythedev.frostengine.modules.gameapi.gamestate.GameState;
import me.frostythedev.frostengine.modules.gameapi.gamestate.core.IStateConstants;
import me.frostythedev.frostengine.modules.gameapi.gamestate.core.StateAction;
import org.bukkit.entity.Player;

public class EndedGameState extends GameState {

    public EndedGameState(Game game) {
        super(game, 3, IStateConstants.IN_GAME_NAME, IStateConstants.IN_GAME_DISPLAY, true);
    }

    @Override
    public void onSwitch() {
        getGame().getGameEndExecutor().endGame(getGame());
    }

    @Override
    public void handle(Player player, StateAction action) {
        if (action.equals(StateAction.JOIN)) {
            if (!player.hasPermission("frostengine.admin")) {
                player.kickPlayer("You do not have sufficient permission to join the game at this time.");
            }
        } else {
            getGame().getTeamManager().clearTeam(player);
        }
    }
}
