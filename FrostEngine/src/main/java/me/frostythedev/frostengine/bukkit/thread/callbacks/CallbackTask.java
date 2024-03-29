package me.frostythedev.frostengine.bukkit.thread.callbacks;

public class CallbackTask implements Runnable {

    private final Runnable task;

    private final Callback callback;

    public CallbackTask(Runnable task, Callback callback) {
        this.task = task;
        this.callback = callback;
    }

    @Override
    public void run() {
        task.run();
        callback.complete();
    }
}
