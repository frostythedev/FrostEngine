package me.frostythedev.frostengine.modules.gameapi.phase;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SinglePlayerVisibilityPhase implements Phase {

    private Player toShow;

    public SinglePlayerVisibilityPhase(Player toShow) {
        this.toShow = toShow;
    }

    @Override
    public void handle(Player player) {
        for(Player ps : Bukkit.getOnlinePlayers()){
            player.hidePlayer(ps);
        }
        player.showPlayer(toShow);
    }

    @Override
    public void unhandle(Player player) {
        for(Player ps : Bukkit.getOnlinePlayers()){
            player.showPlayer(ps);
        }
    }
}
