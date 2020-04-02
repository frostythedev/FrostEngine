package gameapi.exception;

import me.frostythedev.frostengine.bukkit.exceptions.IException;

public class TeamAlreadyLoadedException extends IException{

    public TeamAlreadyLoadedException(String teamName) {
        super("A team with the name '" + teamName + "' has already been loaded.");
    }
}
