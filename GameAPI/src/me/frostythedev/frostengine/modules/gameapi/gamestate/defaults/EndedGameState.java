package gameapi.gamestate.defaults;

import gameapi.gamestate.StateAction;
import gameapi.gamestate.StateConstants;
import gameapi.Minigame;
import gameapi.gamestate.MinigameState;
import org.bukkit.entity.Player;

public class EndedGameState<T extends Minigame> extends MinigameState<T> {

    public EndedGameState(T game) {
        super(game, 3, StateConstants.IN_GAME_NAME, StateConstants.IN_GAME_DISPLAY, true);
    }

    @Override
    public void onSwitch() {
        getMinigame().getGameEndExecutor().endGame();
    }

    @Override
    public void handle(Player player, StateAction action) {
        if (action.equals(StateAction.JOIN)) {
            if (!player.hasPermission("frostengine.admin")) {
                player.kickPlayer("You do not have sufficient permission to join the game at this time.");
            }
        } else {
            getMinigame().getTeamManager().clearTeam(player);
        }
    }
}
