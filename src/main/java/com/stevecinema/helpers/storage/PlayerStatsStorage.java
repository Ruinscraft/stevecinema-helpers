package com.stevecinema.helpers.storage;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class PlayerStatsStorage {

    public abstract CompletableFuture<Void> savePlayerStats(PlayerStats playerStats);

    public abstract CompletableFuture<Void> savePlayerStatsBatch(List<PlayerStats> playerStatsBatch);

    public abstract CompletableFuture<PlayerStats> loadPlayerStats(UUID playerId);

}
