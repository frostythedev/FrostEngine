package me.frostythedev.frostengine.modules.gameapi.core.settings;

public interface PlayerSetting<T> {

    String getName();
    boolean isEnabled();
    void onAction(T event);
}
