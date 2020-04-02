package me.frostythedev.frostengine.bukkit.exception;

import me.frostythedev.frostengine.bukkit.messaging.Locale;

/**
 * Programmed by Tevin on 8/2/2016.
 */
public class DuplicateElementException extends BaseException {

    public DuplicateElementException() {
        super(Locale.ERROR_ELEMENT_DUPLICATION);
    }
}
