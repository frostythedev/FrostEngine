package me.frostythedev.frostengine.data.sqlite;

import me.frostythedev.frostengine.data.core.SQL;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite implements SQL {

    private File file;
    private Connection connection;

    public SQLite(File file) {
        this.file = file;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public boolean hasConnection() {
        try {
            return (getConnection() != null) && (!getConnection().isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean openConnection() throws SQLException {
        if (hasConnection()) {
            return true;
        } else {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (Exception ignored) {
            }
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
            return true;
        }
    }

    @Override
    public void closeConnection() throws SQLException {
        if (hasConnection()) {
            connection.close();
            connection = null;
        }
    }
}
