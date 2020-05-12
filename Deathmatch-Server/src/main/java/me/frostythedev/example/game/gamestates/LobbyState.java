package me.frostythedev.example.game.gamestates;

import me.frostythedev.example.game.DeathmatchGame;
import me.frostythedev.frostengine.modules.gameapi.gamestate.core.StateAction;
import me.frostythedev.frostengine.modules.gameapi.gamestate.defaults.LobbyGameState;
import org.bukkit.entity.Player;

public class LobbyState extends LobbyGameState {

    public LobbyState(DeathmatchGame game) {
        super(game);
    }

    @Override
    public void handle(Player player, StateAction action) {
        super.handle(player, action);

        // Teleport to Lobby
        if(action == StateAction.JOIN){
            if(getGame().getArenaManager().getLobbyArena() != null){
                player.teleport(getGame().getArenaManager().getLobbyArena().getSpawn(0));
            }
        }
    }
}
