package me.frostythedev.frostengine.bukkit.thread;

import com.google.inject.Inject;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class RunnableManager {

    @Inject FEPlugin plugin;

    //private JavaPlugin plugin;
    private HashMap<String, Integer> runningTasks = new HashMap<>();

    private Map<Integer, Runnable> runnableIds = new HashMap<>();

    /*public RunnableManager(JavaPlugin Plugin) {
        this.plugin = Plugin;
    }*/

    //todo implement wrapped class that holds runnable, but outputs to console from the name of the thread that had it's task executed (debug)
    // Example: [GAME UPDATE] Started Tick!

    public int registerSyncRepeatTask(String name, Runnable task, long delayInTicks, long repeatTimeInTicks) {
        int taskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, task, delayInTicks, repeatTimeInTicks);
        runningTasks.put(name, taskId);
        runnableIds.put(taskId, task);
        return taskId;
    }

    public int registerAsyncRepeatTask(String name, Runnable task, long delayInTicks, long repeatTimeInTicks) {
        int taskId = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, task, delayInTicks, repeatTimeInTicks);
        runningTasks.put(name, taskId);
        runnableIds.put(taskId, task);
        return taskId;
    }

    public void runTaskNow(Runnable task) {
        plugin.getServer().getScheduler().runTask(this.plugin, task);
    }

    public void runTaskAsync(Runnable task) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, task);
    }

    public void runTaskLater(Runnable task, long delayInTicks) {
        plugin.getServer().getScheduler().runTaskLater(plugin, task, delayInTicks);
    }

    public void runTaskOneTickLater(Runnable task) {
        plugin.getServer().getScheduler().runTaskLater(plugin, task, 1);
    }

    public void runTaskLaterAsync(Runnable task, long delay) {
        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, task, delay);
    }

    public boolean cancelTask(String name) {
        if (runningTasks.containsKey(name)) {
            Bukkit.getScheduler().cancelTask(runningTasks.get(name));
            runningTasks.remove(name);
            return true;
        }
        return false;
    }

    public void cancelTask(int taskId) {
        plugin.getServer().getScheduler().cancelTask(taskId);
    }

    public void cancelTasks() {
        Logger logger = plugin.getLogger();
        BukkitScheduler scheduler = Bukkit.getScheduler();
        for (Integer i : runningTasks.values()) {
            if (scheduler.isCurrentlyRunning(i) || scheduler.isQueued(i)) {
                scheduler.cancelTask(i);
                logger.info("Cancelled task " + i + " from executing / continuing execution.");
            }
        }
    }
}
