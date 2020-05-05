package me.frostythedev.frostengine.modules.gameapi.gamestate.defaults;

import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import me.frostythedev.frostengine.modules.gameapi.gamestate.GameState;
import me.frostythedev.frostengine.modules.gameapi.gamestate.core.IStateConstants;
import me.frostythedev.frostengine.modules.gameapi.gamestate.core.StateAction;
import me.frostythedev.frostengine.modules.gameapi.teams.GameTeam;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import org.bukkit.entity.Player;

public class InGameState extends GameState {

    public InGameState(Game game) {
        super(game, 2, IStateConstants.IN_GAME_NAME, IStateConstants.IN_GAME_DISPLAY, true);
    }

    @Override
    public void onSwitch() {
        getGame().getSettingManager().toggleOff("Movement");
        getGame().getGameStartExecutor().startGame(getGame());
    }

    @Override
    public void handle(Player player, StateAction action) {
        if (action.equals(StateAction.JOIN)) {
            if(!player.hasPermission("frostengine.admin")){
                player.kickPlayer("You do not have sufficient permission to join the game at this time.");
            }else{
                GameTeam spec = getGame().getTeamManager().getTeam("Spectator");
                if (getGame().getTeamManager().setTeam(player, spec)) {
                    player.sendMessage(Locale.toColors("&e>> You have joined the &b" + spec.getName() + " &eteam!"));
                }
            }
        } else {
            getGame().getTeamManager().clearTeam(player);
        }
    }
}
