package gameapi.exception;

import me.frostythedev.frostengine.bukkit.exceptions.IException;

public class ArenaAlreadyLoadedException extends IException {

    public ArenaAlreadyLoadedException(String arenaName) {
        super("An arena with the name '" + arenaName + "' has already been loaded.");
    }
}
