package com.stevecinema.helpers.spawning;

import org.bukkit.Location;

public class SpawnPoint {

    private Location location;
    private boolean awayFromSpawn;

    public SpawnPoint(Location location, boolean awayFromSpawn) {
        this.location = location;
        this.awayFromSpawn = awayFromSpawn;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isAwayFromSpawn() {
        return awayFromSpawn;
    }

}
