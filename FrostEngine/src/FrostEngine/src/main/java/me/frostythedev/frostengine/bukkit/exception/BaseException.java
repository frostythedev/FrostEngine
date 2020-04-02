package me.frostythedev.frostengine.bukkit.exception;

/**
 * Programmed by Tevin on 8/2/2016.
 */
public class BaseException extends Exception {

    private String errorMessage;

    public BaseException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
