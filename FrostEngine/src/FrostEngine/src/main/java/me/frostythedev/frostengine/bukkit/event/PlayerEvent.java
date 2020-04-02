package me.frostythedev.frostengine.bukkit.event;

import org.bukkit.entity.Player;

/**
 * Programmed by Tevin on 8/2/2016.
 */
public class PlayerEvent extends BaseEvent {

    private Player player;

    public PlayerEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
