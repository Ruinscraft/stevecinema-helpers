package com.stevecinema.helpers.storage;

import java.util.UUID;

public class PlayerStats {

    private UUID playerId;
    private int playMinutes;
    private int kills;
    private int deaths;

    private transient long loginTime;

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
        if (loginTime > 0) {
            int secondsSinceLogin = (int) (System.currentTimeMillis() - loginTime) / 1000;
            int minutesSinceLogin = secondsSinceLogin / 60;
            return playMinutes + minutesSinceLogin;
        } else {
            return playMinutes;
        }
    }

    public int getKills() {
        return kills;
    }

    public void addKill() {
        kills++;
    }

    public int getDeaths() {
        return deaths;
    }

    public void addDeath() {
        deaths++;
    }

    public void setLoginTime() {
        loginTime = System.currentTimeMillis();
    }

}
