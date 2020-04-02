package gameapi.gamestate.defaults;

import me.frostythedev.frostengine.bukkit.messaging.Locale;
import gameapi.gamestate.StateAction;
import gameapi.gamestate.StateConstants;
import gameapi.Minigame;
import gameapi.gamestate.MinigameState;
import gameapi.teams.GameTeam;
import org.bukkit.entity.Player;

public class InGameState <T extends Minigame> extends MinigameState<T> {

    public InGameState(T game) {
        super(game, 2, StateConstants.IN_GAME_NAME, StateConstants.IN_GAME_DISPLAY, true);
    }

    @Override
    public void onSwitch() {
        getMinigame().getGameStartExecutor().startGame();
    }

    @Override
    public void handle(Player player, StateAction action) {
        if (action.equals(StateAction.JOIN)) {
            if(!player.hasPermission("frostengine.admin")){
                player.kickPlayer("You do not have sufficient permission to join the game at this time.");
            }else{
                GameTeam spec = getMinigame().getTeamManager().getTeam("Spectator");
                if (getMinigame().getTeamManager().setTeam(player, spec)) {
                    player.sendMessage(Locale.toColors("&e>> You have joined the &b" + spec.getName() + " &eteam!"));
                }
            }
        } else {
            getMinigame().getTeamManager().clearTeam(player);
        }
    }
}
