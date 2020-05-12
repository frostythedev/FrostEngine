package me.frostythedev.frostengine.data.core;

import me.frostythedev.frostengine.bukkit.utils.LogUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;

public abstract class DatabaseConnector {

    private Connection sqlConnection = null;
    private FileConfiguration config;

    public DatabaseConnector(FileConfiguration sqlConfiguration) {
        this.config = sqlConfiguration;
        initConnection();
    }

    private void initConnection() {
        try {
            LogUtils.info("Attempting to establish a connection the MySQL server!");
            Class.forName("com.mysql.jdbc.Driver");
            sqlConnection = DriverManager.getConnection("jdbc:mysql://" + config.getString("data.MySQL.host") +
                    ":" + config.getString("data.MySQL.port") + "/" + config.getString("data.MySQL.database")
                    + "?autoReconnect=true", config.getString("data.MySQL.username"),
                    config.getString("data.MySQL.password"));
          //  stopwatch.stop();
            LogUtils.warning("Connection to MySQL server established! (" + config.getString("data.MySQL.host") +
                    ":" + config.getString("data.MySQL.port") + ")");
            //Chat.debug("Connection took " + stopwatch + "ms!");
        } catch (SQLException e) {
            LogUtils.warning("Could not connect to MySQL server! because: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            LogUtils.warning("JDBC Driver not found!");
        }
    }

    public PreparedStatement prepareStatement(String sqlStatement) {
        try {
            return sqlConnection.prepareStatement(sqlStatement);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isConnected() {
        return sqlConnection != null;
    }

    public void closeConnection() {
        try {
            sqlConnection.close();
        } catch (SQLException Ex) {
            System.out.println("Couldn't close Connection");
        }
    }

    public void closeQuietly(ResultSet rs) {
        PreparedStatement ps = null;
        try {
            ps = (PreparedStatement) rs.getStatement();
            rs.close();
            rs = null;
            ps.close();
            ps = null;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // Ignore... nothing we can do about this here
                }
            }

            if (ps != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // Ignore... nothing we can do about this here
                }
            }
        }
    }

    public Connection getSqlConnection() {
        return sqlConnection;
    }

    public boolean close(PreparedStatement preparedStatement) {
        if (preparedStatement == null) {
            return false;
        }

        boolean closed = false;
        try {
            preparedStatement.close();
            closed = preparedStatement.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return closed;
    }

    public boolean[] close(PreparedStatement... statements) {
        boolean[] closed = new boolean[statements.length];
        for (int i = 0; i < statements.length; i++) {
            closed[i] = close(statements[i]);
        }
        return closed;
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
