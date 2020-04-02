package me.frostythedev.frostengine.bukkit.gameapi.core.threads;

import me.frostythedev.frostengine.bukkit.gameapi.Minigame;
import me.frostythedev.frostengine.bukkit.gameapi.core.GameConstants;
import me.frostythedev.frostengine.bukkit.gameapi.threads.EZCountdown;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import org.bukkit.Bukkit;

public class GameCountdown extends EZCountdown {

    private Minigame minigame;

    public GameCountdown(Minigame minigame) {
        super(GameConstants.TICKS, GameConstants.TICKS_ARRAY);

        this.minigame = minigame;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onTick() {
        if(minigame.getArenaManager().getArenas().size() <= 0){
            //TODO Error logging, cannot continue with minigame because there are no arenas to be used, shutdown
        }else{
            minigame.setArena(minigame.getArenaManager().getFreeArena(minigame.getMaxPlayers()));
            if(minigame.getArena() == null){
                //TODO No arenas could be found to handle the amount of players of this game, shutdown
            }else{
                //TODO continue with other game actions
            }
        }

        if (Bukkit.getOnlinePlayers().size() < minigame.getMinPlayers()) {
            setTicks(GameConstants.TICKS);
        }
    }

    @Override
    public void onInterval() {
      /*  System.out.println("onInterval call from: " + this.getClass().getName());
        System.out.println("taskId: " + getTask().getTaskId());*/
        Bukkit.broadcastMessage(Locale.toColors("&7The game will begin in &e" + getTicks() + " seconds!"));
    }

    @Override
    public void onEnd() {
        minigame.switchState(1);
    }
}
