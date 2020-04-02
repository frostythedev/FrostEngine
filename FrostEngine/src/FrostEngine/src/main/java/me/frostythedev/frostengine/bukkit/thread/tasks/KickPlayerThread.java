package me.frostythedev.frostengine.bukkit.thread.tasks;

import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class KickPlayerThread implements Runnable {
    private UUID id;
    private String reason;

    public KickPlayerThread(UUID id, String reason) {
        this.id = id;
        this.reason = reason;
    }

    @Override
    public void run() {
        Bukkit.getPlayer(id).kickPlayer(reason);
       // PlayerUtil.kick(PlayerUtil.getPlayer(id), reason);
    }
}
