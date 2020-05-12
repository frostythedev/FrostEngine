package me.frostythedev.frostengine.data.mysql;

import me.frostythedev.frostengine.bukkit.thread.Tasks;
import me.frostythedev.frostengine.config.BukkitDocument;
import me.frostythedev.frostengine.data.core.SQL;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;

public class MySQL implements SQL {

    private JavaPlugin plugin;
    private Connection connection;

    public MySQL(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public boolean hasConnection() {
        try {
            return (getConnection() != null) && (!getConnection().isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean openConnection() throws SQLException{

        if (hasConnection()) {
            return true;
        }

        if (!hasConnection()) {

            BukkitDocument document = BukkitDocument.of(plugin.getDataFolder() + "/mysql.yml");

            if (!document.exists()) {
                document.create(true);
                document.set("MySQL.host", "localhost");
                document.set("MySQL.port", "3306");
                document.set("MySQL.database", "mj_db_1");
                document.set("MySQL.username", "root");
                document.set("MySQL.password", "password");
                Tasks.run(document);
                return false;
            }

            document.load();

            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println("jdbc driver unavailable!");
                return false;
            }
            String host = document.getString("MySQL.host");
            String port = document.getString("MySQL.port");
            String database = document.getString("MySQL.database");
            String username = document.getString("MySQL.username");
            String password = document.getString("MySQL.password");

            String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

            connection = DriverManager.getConnection(url, username, password);
            return true;
        }
        return false;
    }

    public void closeConnection() throws SQLException{
        if (hasConnection()) {
            this.connection.close();
            this.connection = null;
        }
    }

    public boolean syncUpdate(String query) {
        Statement stmt = null;
        try {
            stmt = getConnection().createStatement();
            return stmt.executeUpdate(query) != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public ResultSet syncQuery(String query) {
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs != null) {
                return rs;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
