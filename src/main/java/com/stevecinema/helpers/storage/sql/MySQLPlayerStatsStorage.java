package com.stevecinema.helpers.storage.sql;

import com.stevecinema.helpers.storage.PlayerStats;

import java.sql.*;
import java.util.UUID;

public abstract class MySQLPlayerStatsStorage extends SQLPlayerStatsStorage {

    @Override
    public void createTables(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS player_stats (" +
                    "player_id VARCHAR(36) NOT NULL, " +
                    "play_minutes INT DEFAULT 0, " +
                    "kills INT DEFAULT 0, " +
                    "deaths INT DEFAULT 0, " +
                    "PRIMARY KEY (player_id));");
        }
    }

    @Override
    public void upsertPlayerStats(PlayerStats playerStats, Connection connection) throws SQLException {
        try (PreparedStatement upsert = connection.prepareStatement("INSERT INTO player_stats (player_id) VALUES (?) ON DUPLICATE KEY UPDATE play_minutes = ?, kills = ?, deaths = ?;")) {
            upsert.setString(1, playerStats.getPlayerId().toString());
            upsert.setInt(2, playerStats.getPlayMinutes());
            upsert.setInt(3, playerStats.getKills());
            upsert.setInt(4, playerStats.getDeaths());
            upsert.execute();
        }
    }

    @Override
    public PlayerStats queryPlayerStats(UUID playerId, Connection connection) throws SQLException {
        try (PreparedStatement query = connection.prepareStatement("SELECT * FROM player_stats WHERE player_id = ?;")) {
            query.setString(1, playerId.toString());

            try (ResultSet resultSet = query.executeQuery()) {
                if (resultSet.next()) {
                    int playMinutes = resultSet.getInt("play_minutes");
                    int kills = resultSet.getInt("kills");
                    int deaths = resultSet.getInt("deaths");
                    return new PlayerStats(playerId, playMinutes, kills, deaths);
                }
            }
        }

        return null;
    }

}
