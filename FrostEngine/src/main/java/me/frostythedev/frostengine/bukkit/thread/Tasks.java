package me.frostythedev.frostengine.bukkit.thread;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.List;

public class Tasks {
    
    @Inject
    private static FEPlugin plugin;

    public static BukkitTask runOneTickLater(Runnable runnable){
        return runLater(runnable, 1);
    }

    public static BukkitTask run(Runnable runnable) {
        return Bukkit.getScheduler().runTask(plugin, runnable);
    }

    public static BukkitTask runLater(Runnable runnable, long delayInTicks) {
        return Bukkit.getScheduler().runTaskLater(plugin, runnable, delayInTicks);
    }


    public static BukkitTask runLaterAsync(Runnable runnable, long delayInTicks) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delayInTicks);
    }


    public static BukkitTask runRepeating(Runnable runnable, long delayInTicks) {
        return Tasks.runRepeating(runnable, delayInTicks, delayInTicks);
    }


    public static BukkitTask runRepeatingAsync(Runnable runnable, long delayInTicks) {
        return Tasks.runRepeatingAsync(runnable, delayInTicks, delayInTicks);
    }


    public static BukkitTask runRepeating(Runnable runnable, long startDelayInTicks, long delayInTicks) {
        return Bukkit.getScheduler().runTaskTimer(plugin, runnable, startDelayInTicks, delayInTicks);
    }


    public static BukkitTask runRepeatingAsync(Runnable runnable, long startDelayInTicks, long delayInTicks) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, startDelayInTicks, delayInTicks);
    }


    public static BukkitTask runAsync(Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }


    public static List<BukkitTask> run(Collection<Runnable> runnables) {
        List<BukkitTask> tasks = Lists.newArrayList();

        for (Runnable runnable : runnables) {
            tasks.add(Tasks.run(runnable));
        }

        return tasks;
    }

    public static List<BukkitTask> runSeparately(Collection<Runnable> runnables, long increment) {
        List<BukkitTask> tasks = Lists.newArrayList();

        long base = 0;

        for (Runnable runnable : runnables) {
            tasks.add(Tasks.runLater(runnable, base));

            base += increment;
        }

        return tasks;
    }


    public static void cancel(BukkitTask task) {
        Tasks.cancel(task.getTaskId());
    }


    public static void cancel(int task) {
        Bukkit.getScheduler().cancelTask(task);
    }


    public static BukkitTask cancelLater(int task, long delay) {
        return Tasks.runLater(() -> Bukkit.getScheduler().cancelTask(task), delay);
    }


    public static void cancel(Collection<Integer> tasks) {
        for (int task : tasks) {
            Tasks.cancel(task);
        }
    }

    public static List<BukkitTask> cancelSeparately(Collection<Integer> tasks, long increment) {
        List<BukkitTask> cancelTasks = Lists.newArrayList();

        long base = 0;

        for (int task : tasks) {
            cancelTasks.add(Tasks.cancelLater(task, base));

            base += increment;
        }

        return cancelTasks;
    }
}