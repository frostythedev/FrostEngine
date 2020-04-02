package me.frostythedev.frostengine.bukkit.gameapi.threads;

import me.frostythedev.frostengine.bukkit.thread.Tasks;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;

/**
 * Programmed by Tevin on 3/30/2016.
 */
public abstract class EZCountdown implements Runnable {

    private BukkitTask task;

    private int ticks;
    private int[] intervals;

    public EZCountdown(int ticks, int[] intervals) {
        this.ticks = ticks;
        this.intervals = intervals;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public int[] getIntervals() {
        return intervals;
    }

    public void setIntervals(int[] intervals) {
        this.intervals = intervals;
    }

    public void schedule() {
        onStart();
        task = Tasks.runRepeating(this, 20);
    }

    public void setLevels(Collection<Player> players) {
        for (Player ps : players) {
            ps.setLevel(getTicks());
        }
    }

    public abstract void onStart();

    public abstract void onTick();

    public abstract void onInterval();

    public abstract void onEnd();

    @Override
    public void run() {
        onTick();

        for (int i : getIntervals()) {
            if (getTicks() == i) {
                onInterval();
            }
        }

        if (getTicks() == 0) {
            onEnd();
            Tasks.cancel(task);
        }

        setTicks(getTicks() - 1);
    }

    public BukkitTask getTask() {
        return task;
    }
}
