package me.frostythedev.frostengine.data.core;

import java.sql.Connection;
import java.sql.SQLException;

public interface SQL {

    Connection getConnection() throws SQLException;

    boolean hasConnection();

    boolean openConnection() throws SQLException;

    void closeConnection() throws SQLException;
}
