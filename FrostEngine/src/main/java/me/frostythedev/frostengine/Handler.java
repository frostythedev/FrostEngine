package me.frostythedev.frostengine;

public interface Handler {

    /**
     * Called when this handler is enabled
     */
    void enable();

    /**
     * Called when this handler is disabled
     */
    void disable();
}
