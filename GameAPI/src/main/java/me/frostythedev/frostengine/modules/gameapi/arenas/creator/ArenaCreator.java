package me.frostythedev.frostengine.modules.gameapi.arenas.creator;

import me.frostythedev.frostengine.modules.gameapi.arenas.GameArena;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ArenaCreator {

    private UUID uuid;
    private GameArena arena;

    public ArenaCreator(Player player) {
        this.uuid = player.getUniqueId();
    }

    public UUID getUuid() {
        return uuid;
    }

    public GameArena getArena() {
        return arena;
    }

    public void setArena(GameArena arena) {
        this.arena = arena;
    }
}
