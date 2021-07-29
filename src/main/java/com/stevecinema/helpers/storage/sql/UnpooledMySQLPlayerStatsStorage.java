package com.stevecinema.helpers.storage.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UnpooledMySQLPlayerStatsStorage extends MySQLPlayerStatsStorage {

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    public UnpooledMySQLPlayerStatsStorage(String host, int port, String database, String username, String password) throws SQLException {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;

        try (Connection connection = getConnection()) {
            createTables(connection);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false";
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

}
