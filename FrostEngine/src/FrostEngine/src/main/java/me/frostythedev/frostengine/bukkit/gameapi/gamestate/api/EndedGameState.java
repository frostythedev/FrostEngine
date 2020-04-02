package me.frostythedev.frostengine.bukkit.gameapi.gamestate.api;

import me.frostythedev.frostengine.bukkit.gameapi.Minigame;
import me.frostythedev.frostengine.bukkit.gameapi.gamestate.IStateConstants;
import me.frostythedev.frostengine.bukkit.gameapi.gamestate.StateAction;
import me.frostythedev.frostengine.bukkit.gameapi.gamestate.MinigameState;
import org.bukkit.entity.Player;

public class EndedGameState<T extends Minigame> extends MinigameState<T> {

    public EndedGameState(T game) {
        super(game, 3, IStateConstants.IN_GAME_NAME, IStateConstants.IN_GAME_DISPLAY, true);
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
