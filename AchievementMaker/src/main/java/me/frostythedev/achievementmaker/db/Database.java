package me.frostythedev.achievementmaker.db;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class Database {

    private HikariDataSource hikari;

    public Database(String address, String database, String username, String password) {
        try {
            // Force driver to load if not yet loaded
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        hikari = new HikariDataSource();
        hikari.setMaximumPoolSize(10);
        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", address);
        hikari.addDataSourceProperty("port", "3306");
        hikari.addDataSourceProperty("databaseName", database);
        hikari.addDataSourceProperty("user", username);
        hikari.addDataSourceProperty("password", password);

        /* CREATE TABLES */

       /* createTable("user_info", "id INT PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(36), username VARCHAR(16), firstlogin TIMESTAMP");
        createTable("user_ranks", "id INT PRIMARY KEY AUTO_INCREMENT, storage_id INT, server VARCHAR(12), rank VARCHAR(20)");
        createTable("user_permissions", "id INT PRIMARY KEY AUTO_INCREMENT, storage_id INT, server VARCHAR(12), permission VARCHAR(100)");

        createTable("rank_management", "id INT PRIMARY KEY AUTO_INCREMENT, rank VARCHAR(20), server VARCHAR(12), prefix VARCHAR(20), suffix VARCHAR(20), text VARCHAR(6), multiplier INT, tabvalue INT, value INT");
        createTable("rank_permissions", "id INT PRIMARY KEY AUTO_INCREMENT, rank VARCHAR(20), server VARCHAR(12), permission VARCHAR(100)");

        createTable("nick_management", "id INT PRIMARY KEY AUTO_INCREMENT, storage_id INT, server VARCHAR (12), nick VARCHAR(50)");

        createTable("silk_management", "id INT PRIMARY KEY AUTO_INCREMENT, storage_id INT, silk INT");
        createTable("silk_history", "id INT PRIMARY KEY AUTO_INCREMENT, storage_id INT, server VARCHAR(12), reason VARCHAR(100), value INT, total INT, time TIMESTAMP");
    */
    }

    /**
     * @return database
     */

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
            System.out.println(e.getMessage());
            try {
                System.out.println(stat.toString());
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
            System.out.println(e.getMessage());
            try {
                System.out.println(stat.toString());
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
            System.out.println(e.getMessage());
            try {
                System.out.println(stat.toString());
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
            System.out.println(e.getMessage());
            try {
                System.out.println(stat.toString());
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
            System.out.println(e.getMessage());
            try {
                System.out.println(stat.toString());
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
            System.out.println(e.getMessage());
            try {
                System.out.println(stat.toString());
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
            return !hikari.getConnection().isClosed();
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
