package com.stevecinema.helpers.storage;

import java.util.UUID;

public class PlayerStats {

    private UUID playerId;
    private int playMinutes;
    private int kills;
    private int deaths;

    public PlayerStats(UUID playerId) {
        this.playerId = playerId;
    }

    public PlayerStats(UUID playerId, int playMinutes, int kills, int deaths) {
        this.playerId = playerId;
        this.playMinutes = playMinutes;
        this.kills = kills;
        this.deaths = deaths;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getPlayMinutes() {
        return playMinutes;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

}
