package me.frostythedev.frostengine.bukkit.gameapi.core.utilities;

import me.frostythedev.frostengine.bukkit.gameapi.Minigame;

public interface GameUtility<T extends Minigame> {

    T getMinigame();

    default void start(){

    }

    default void end(){

    }

}
