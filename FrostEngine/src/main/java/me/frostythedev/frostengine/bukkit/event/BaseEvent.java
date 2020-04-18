package me.frostythedev.frostengine.bukkit.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Programmed by Tevin on 8/2/2016.
 */
public class BaseEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private boolean cancelled;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public void callEvent() {
        Bukkit.getServer().getPluginManager().callEvent(this);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
