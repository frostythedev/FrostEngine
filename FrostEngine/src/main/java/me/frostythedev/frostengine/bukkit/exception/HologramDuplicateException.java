package me.frostythedev.frostengine.bukkit.exception;

import me.frostythedev.frostengine.bukkit.messaging.Locale;

/**
 * Programmed by Tevin on 8/2/2016.
 */
public class HologramDuplicateException extends BaseException {

    public HologramDuplicateException() {
        super(Locale.ERROR_HOLOGRAM_DUPLICATION);
    }
}
