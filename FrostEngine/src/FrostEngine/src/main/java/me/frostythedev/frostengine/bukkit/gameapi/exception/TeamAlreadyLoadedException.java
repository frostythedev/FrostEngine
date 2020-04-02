package me.frostythedev.frostengine.bukkit.gameapi.exception;

import me.frostythedev.frostengine.bukkit.exception.BaseException;

public class TeamAlreadyLoadedException extends BaseException {

    public TeamAlreadyLoadedException(String teamName) {
        super("A team with the name '" + teamName + "' has already been loaded.");
    }
}
