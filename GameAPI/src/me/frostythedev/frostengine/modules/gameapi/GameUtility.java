package gameapi.

public interface GameUtility<T extends Minigame> {

    T getMinigame();

    default void start(){

    }

    default void end(){

    }

}
