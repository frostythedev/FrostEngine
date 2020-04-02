package me.frostythedev.frostengine.data.core;

import com.zaxxer.hikari.HikariDataSource;
import me.frostythedev.frostengine.bukkit.debug.Debugger;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.logging.Level;

public class Database {

    private static Database instance;

    private HikariDataSource hikari;

    private Database() {

        FileConfiguration config = FEPlugin.get().getConfig();
        try {
            // Force driver to load if not yet loaded
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        String address = config.getString("MySQL.Address");
        String name = config.getString("MySQL.Name");
        String username = config.getString("MySQL.Username");
        String password = config.getString("MySQL.Password");

        hikari = new HikariDataSource();
        hikari.setMaximumPoolSize(10);
        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", address);
        hikari.addDataSourceProperty("port", "3306");
        hikari.addDataSourceProperty("databaseName", name);
        hikari.addDataSourceProperty("user", username);
        hikari.addDataSourceProperty("password", password);

        createTable(DatabaseField.ARENA_TABLE, DatabaseField.ARENA_VALUES);
        createTable(DatabaseField.KIT_TABLE, DatabaseField.KIT_VALUES);
        createTable(DatabaseField.PLAYERS_TABLE, DatabaseField.PLAYER_VALUES);
    }

    /**
     * @return database
     */
    public static Database get() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * @param query
     * @param parameters
     * @param callback
     */
    public void syncQuery(String query, Object[] parameters, Callback callback) {
        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = hikari.getConnection();
            stat = con.prepareStatement(query);
            injectParameters(stat, parameters);
            stat.execute();
            rs = stat.getResultSet();
            callback.read(rs);
            tryClose(rs);
            tryClose(stat);
            tryClose(con);
            callback.digestSync();
        } catch (SQLException e) {
            e.printStackTrace();
            Debugger.log(Level.SEVERE, e.getMessage());
            try {
                Debugger.log(Level.SEVERE, stat.toString());
            } catch (Exception e1) {
            }
        } finally {
            tryClose(rs);
            tryClose(stat);
            tryClose(con);
        }
    }

    /**
     * @param query
     * @param parameters
     * @param callback
     */
    public void asyncQueryCallback(final String query, final Object[] parameters, final Callback callback) {
        new BukkitRunnable() {
            public void run() {
                Connection con = null;
                PreparedStatement stat = null;
                ResultSet rs = null;
                try {
                    con = hikari.getConnection();
                    stat = con.prepareStatement(query);
                    injectParameters(stat, parameters);
                    stat.execute();
                    rs = stat.getResultSet();
                    callback.read(rs);
                    tryClose(rs);
                    tryClose(stat);
                    tryClose(con);
                    callback.digestAsync();
                    new BukkitRunnable() {
                        public void run() {
                            callback.digestSync();
                        }
                    }.runTask(FEPlugin.get());
                } catch (SQLException e) {
                    e.printStackTrace();
                    Debugger.log(Level.SEVERE, e.getMessage());
                } finally {
                    tryClose(rs);
                    tryClose(stat);
                    tryClose(con);
                }
            }
        }.runTaskAsynchronously(FEPlugin.get());
    }

    /**
     * @param query
     * @param parameters
     * @param callback
     */
    public void asyncSyncQuery(String query, Object[] parameters, Callback callback) {
        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = hikari.getConnection();
            stat = con.prepareStatement(query);
            injectParameters(stat, parameters);
            stat.execute();
            rs = stat.getResultSet();
            callback.read(rs);
            callback.digestAsync();
        } catch (SQLException e) {
            e.printStackTrace();
            Debugger.log(Level.SEVERE, e.getMessage());
            try {
                Debugger.log(Level.SEVERE, stat.toString());
            } catch (Exception e1) {
            }
        } finally {
            tryClose(rs);
            tryClose(stat);
            tryClose(con);
        }
    }

    /**
     * @param query
     * @param parameters
     * @return integer
     */
    public int syncUpdate(final String query, final Object[] parameters) {
        Connection con = null;
        PreparedStatement stat = null;
        try {
            con = hikari.getConnection();
            stat = con.prepareStatement(query);
            injectParameters(stat, parameters);
            return stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            Debugger.log(Level.SEVERE, e.getMessage());
            try {
                Debugger.log(Level.SEVERE, stat.toString());
            } catch (Exception e1) {
            }
        } finally {
            tryClose(stat);
            tryClose(con);
        }
        return -1;
    }

    /**
     * @param query
     * @param parameters
     */
    public void asyncUpdate(final String query, final Object[] parameters) {
        new BukkitRunnable() {
            public void run() {
                Connection con = null;
                PreparedStatement stat = null;
                try {
                    con = hikari.getConnection();
                    stat = con.prepareStatement(query);
                    injectParameters(stat, parameters);
                    stat.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Debugger.log(Level.SEVERE, e.getMessage());
                    try {
                        Debugger.log(Level.SEVERE, stat.toString());
                    } catch (Exception e1) {
                    }
                } finally {
                    tryClose(stat);
                    tryClose(con);
                }
            }
        }.runTaskAsynchronously(FEPlugin.get());
    }

    /**
     * @param query
     * @param parameters
     * @return integer
     */
    public int asyncSyncUpdate(String query, final Object[] parameters) {
        Connection con = null;
        PreparedStatement stat = null;
        try {
            con = hikari.getConnection();
            stat = con.prepareStatement(query);
            injectParameters(stat, parameters);
            return stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            Debugger.log(Level.SEVERE, e.getMessage());
            try {
                Debugger.log(Level.SEVERE, stat.toString());
            } catch (Exception e1) {
            }
        } finally {
            tryClose(stat);
            tryClose(con);
        }
        return -1;
    }

    /**
     * @param query
     * @param parameters
     * @return integer
     */
    public int syncInsert(final String query, final Object[] parameters) {
        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = hikari.getConnection();
            stat = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            injectParameters(stat, parameters);
            rs = stat.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            Debugger.log(Level.SEVERE, e.getMessage());
            try {
                Debugger.log(Level.SEVERE, stat.toString());
            } catch (Exception e1) {
            }
        } finally {
            tryClose(rs);
            tryClose(stat);
            tryClose(con);
        }
        return -1;
    }

    /**
     * @param query
     * @param parameters
     * @return integer
     */
    public int asyncSyncInsert(String query, final Object[] parameters) {
        Connection con = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            con = hikari.getConnection();
            stat = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            injectParameters(stat, parameters);
            rs = stat.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            Debugger.log(Level.SEVERE, e.getMessage());
            try {
                Debugger.log(Level.SEVERE, stat.toString());
            } catch (Exception e1) {
            }
        } finally {
            tryClose(rs);
            tryClose(stat);
            tryClose(con);
        }
        return -1;
    }

    /**
     * @param tableName
     * @param fields
     */
    public void createTable(String tableName, String fields) {
        syncUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (" + fields + ")", null);
    }

    public boolean hasConnection(){
        try {
            return hikari != null && !hikari.getConnection().isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param stat
     * @param parameters
     * @throws SQLException
     */
    private void injectParameters(PreparedStatement stat, Object[] parameters) throws SQLException {
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                Object param = parameters[i];
                if (param instanceof String) {
                    stat.setString((i + 1), String.valueOf(param));
                } else if (param instanceof Integer) {
                    stat.setInt((i + 1), (Integer) param);
                } else if (param instanceof Long) {
                    stat.setLong((i + 1), (Long) param);
                } else if (param instanceof Timestamp) {
                    stat.setTimestamp((i + 1), (Timestamp) param);
                } else if (param instanceof Boolean) {
                    stat.setBoolean((i + 1), (Boolean) param);
                }
            }
        }
    }

    private void tryClose(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
            }
        }
    }
}
