package me.frostythedev.frostengine.modules.gameapi.core.threads;

import me.frostythedev.frostengine.modules.gameapi.Minigame;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.Game;
import me.frostythedev.frostengine.modules.gameapi.core.interfaces.GameConstants;
import me.frostythedev.frostengine.modules.gameapi.threads.EZCountdown;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

public class GameCountdown extends EZCountdown {

    private Game minigame;

    public GameCountdown(Game minigame) {
        super(GameConstants.TICKS, GameConstants.TICKS_ARRAY);

        this.minigame = minigame;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onTick() {
        Bukkit.getOnlinePlayers().forEach(p -> p.setLevel(getTicks()));
        if (Bukkit.getOnlinePlayers().size() < minigame.getMinPlayers()) {
            setTicks(GameConstants.TICKS);
        }
    }

    @Override
    public void onInterval() {

        Bukkit.getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), Sound.ORB_PICKUP,0.6f,0.6f));


        if(minigame.getArenaManager().getArenas().size() <= 0){
            //TODO Error logging, cannot continue with minigame because there are no arenas to be used, shutdown
//            Debugger.log(Level.SEVERE, "There are no arenas to load for this mingiame!");
           Locale.broadcast("frostengine.admin", "&c>> There are no arenas to load for this minigame!");
            setTicks(GameConstants.TICKS);
        }else{
            minigame.setArena(minigame.getArenaManager().getFreeArena(minigame.getMaxPlayers()));
            if(minigame.getGameArena() == null){
                //TODO No arenas could be found to handle the amount of players of this game, shutdown
                //Debugger.log(Level.SEVERE, "No arena could be found to handle the amount of players");

                Locale.broadcast("frostengine.admin", "&c>> No arena could be found to handle the amount of players");
                setTicks(GameConstants.TICKS);
            }else{
                //TODO continue with other game actions
                Bukkit.broadcastMessage(Locale.toColors("&7The game will begin in &e" + getTicks() + " seconds!"));

            }
        }

      /*  System.out.println("onInterval call from: " + this.getClass().getName());
        System.out.println("taskId: " + getTask().getTaskId());*//*
        Bukkit.broadcastMessage(Locale.toColors("&7The game will begin in &e" + getTicks() + " seconds!"));
    */
    }

    @Override
    public void onEnd() {
        minigame.switchState(1);
    }
}
