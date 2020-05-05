package me.frostythedev.frostengine.modules.gameapi.core.utilities;

import me.frostythedev.frostengine.modules.gameapi.Minigame;

public interface GameUtility<T extends Minigame> {

    T getMinigame();

    default void start(){

    }

    default void end(){

    }

}
