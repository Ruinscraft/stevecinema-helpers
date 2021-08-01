package com.stevecinema.helpers.spawning;

import com.stevecinema.helpers.HelpersPlugin;
import com.stevecinema.helpers.util.WorldGuardUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SpawnPointManager implements Listener {

    private static final Random RANDOM = new Random();

    private HelpersPlugin helpersPlugin;
    private List<SpawnPoint> spawnPoints;
    private List<SpawnPoint> pitSpawnPoints;
    private Map<Player, Integer> recentDeaths;

    public SpawnPointManager(HelpersPlugin helpersPlugin) {
        this.helpersPlugin = helpersPlugin;
        spawnPoints = new ArrayList<>();
        pitSpawnPoints = new ArrayList<>();
        recentDeaths = new ConcurrentHashMap<>();
        long fiveMinsInTicks = 20L * 60 * 5;
        helpersPlugin.getServer().getScheduler().runTaskTimer(helpersPlugin, () -> {

        }, fiveMinsInTicks, fiveMinsInTicks);
    }

    public void addSpawnPoint(SpawnPoint spawnPoint) {

    }

    public void addPitSpawnPoint(SpawnPoint spawnPoint) {

    }

    public SpawnPoint getRandomSpawnPoint(boolean requireAwayFromSpawn) {
        if (requireAwayFromSpawn) {
            return spawnPoints.stream()
                    .filter(SpawnPoint::isAwayFromSpawn)
                    .collect(Collectors.toList())
                    .get(RANDOM.nextInt(spawnPoints.size()));
        } else {
            return spawnPoints.get(RANDOM.nextInt(spawnPoints.size()));
        }
    }

    public SpawnPoint getRandomPitSpawnPoint() {
        return pitSpawnPoints.get(RANDOM.nextInt(pitSpawnPoints.size()));
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        player.spigot().respawn();

        final SpawnPoint spawnPoint;

        if (WorldGuardUtil.playerInPit(player)) {
            spawnPoint = getRandomPitSpawnPoint();
        } else {
            spawnPoint = getRandomSpawnPoint(false);
        }

        player.teleport(spawnPoint.getLocation());
    }

}
