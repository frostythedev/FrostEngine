package me.frostythedev.frostengine.modules.gameapi.exception;

import me.frostythedev.frostengine.bukkit.exception.BaseException;

public class ArenaAlreadyLoadedException extends BaseException {

    public ArenaAlreadyLoadedException(String arenaName) {
        super("An arena with the name '" + arenaName + "' has already been loaded.");
    }
}
