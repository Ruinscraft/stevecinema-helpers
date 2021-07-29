package com.stevecinema.helpers.storage.sql;

import com.stevecinema.helpers.storage.PlayerStats;
import com.stevecinema.helpers.storage.PlayerStatsStorage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class SQLPlayerStatsStorage extends PlayerStatsStorage {

    @Override
    public CompletableFuture<Void> savePlayerStats(PlayerStats playerStats) {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection()) {
                upsertPlayerStats(playerStats, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public CompletableFuture<Void> savePlayerStatsBatch(List<PlayerStats> playerStatsBatch) {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection()) {
                upsertPlayerStatsBatch(playerStatsBatch, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public CompletableFuture<PlayerStats> loadPlayerStats(UUID playerId) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection()) {
                PlayerStats query = queryPlayerStats(playerId, connection);
                if (query != null) return query;
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return new PlayerStats(playerId);
        });
    }

    public abstract Connection getConnection() throws SQLException;

    public abstract void createTables(Connection connection) throws SQLException;

    public abstract void upsertPlayerStats(PlayerStats playerStats, Connection connection) throws SQLException;

    public abstract void upsertPlayerStatsBatch(List<PlayerStats> playerStatsBatch, Connection connection) throws SQLException;

    public abstract PlayerStats queryPlayerStats(UUID playerId, Connection connection) throws SQLException;

}
