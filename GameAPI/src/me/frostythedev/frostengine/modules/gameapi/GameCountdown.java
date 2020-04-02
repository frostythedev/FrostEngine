package gameapi.

import me.frostythedev.frostengine.bukkit.messaging.Locale;
import gameapi.threads.EZCountdown;
import org.bukkit.Bukkit;

public class GameCountdown extends EZCountdown {

    private Minigame minigame;

    public GameCountdown(Minigame minigame) {
        super(GameConstants.TICKS, GameConstants.TICKS_ARRAY);

        this.minigame = minigame;
    }

    @Override
    public void onStart() {
        Bukkit.broadcastMessage(Locale.toColors("&7The game will begin shortly."));
    }

    @Override
    public void onTick() {
        if (Bukkit.getOnlinePlayers().size() < minigame.getMinPlayers()) {
            setTicks(GameConstants.TICKS);
        }
    }

    @Override
    public void onInterval() {
        System.out.println("onInterval call from: " + this.getClass().getName());
        System.out.println("taskId: " + getTask().getTaskId());
        Bukkit.broadcastMessage(Locale.toColors("&7The game will begin in &e" + getTicks() + " seconds!"));
    }

    @Override
    public void onEnd() {
        minigame.setGameState(minigame.getGameState(1));
    }
}
