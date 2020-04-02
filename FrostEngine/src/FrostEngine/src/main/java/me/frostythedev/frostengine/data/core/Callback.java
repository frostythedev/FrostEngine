package me.frostythedev.frostengine.data.core;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Callback<T> {

    /**
     * Read player from the resultset, should not do any player processing
     *
     * @param rs
     * @throws SQLException
     */
    void read(ResultSet rs) throws SQLException;

    /**
     * This is called on the main thread after {@link #read(ResultSet)} and {@link #digestAsync()} use this
     * for any Bukkit API work
     */
    void digestSync();


    /**
     * This is called after {@link #read(ResultSet)} on an async thread when used in Async Queries,
     * should be used for heavy lifting thats done without using Bukkit API to prep
     * for the {@link #digestSync()}
     */
    void digestAsync();

    /**
     * Should only be called on Sync Queries
     *
     * @return
     */
    T result();
}

