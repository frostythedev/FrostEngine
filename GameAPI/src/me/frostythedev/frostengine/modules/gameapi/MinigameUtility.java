package gameapi.

public class MinigameUtility<T extends Minigame> implements GameUtility<T> {

    private T minigame;

    public MinigameUtility(T minigame) {
        this.minigame = minigame;
    }

    @Override
    public T getMinigame() {
        return minigame;
    }
}
