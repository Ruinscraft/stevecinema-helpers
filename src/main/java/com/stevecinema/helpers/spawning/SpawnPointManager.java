package com.stevecinema.helpers.spawning;

import com.stevecinema.helpers.HelpersPlugin;
import com.stevecinema.helpers.util.WorldGuardUtil;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.*;

public class SpawnPointManager implements Listener {

    private static final Random RANDOM = new Random();

    private HelpersPlugin helpersPlugin;
    private List<Location> spawnPointsAtSpawn;
    private List<Location> spawnPointsAtPit;
    private List<Location> spawnPointsAwayFromSpawn;
    private Map<Player, Integer> recentDeaths;

    public SpawnPointManager(HelpersPlugin helpersPlugin) {
        this.helpersPlugin = helpersPlugin;
        spawnPointsAtSpawn = loadSpawnPoints("spawnpoints");
        spawnPointsAtPit = loadSpawnPoints("pitspawnpoints");
        spawnPointsAwayFromSpawn = loadSpawnPoints("remotespawnpoints");
        long fiveMinsInTicks = 20L * 60 * 5;
        helpersPlugin.getServer().getScheduler().runTaskTimer(helpersPlugin, () -> {

        }, fiveMinsInTicks, fiveMinsInTicks);
        helpersPlugin.getServer().getPluginManager().registerEvents(this, helpersPlugin);
    }

    public void saveSpawnPoint(Location location, String nodeName) {
       ConfigurationSection section = helpersPlugin.getConfig().getConfigurationSection(nodeName);
       if (section == null) {
           section = helpersPlugin.getConfig().createSection(nodeName);
       }
       String id = UUID.randomUUID().toString().substring(0, 8);
       section.set(id, location);
       helpersPlugin.saveConfig();
    }

    public List<Location> loadSpawnPoints(String nodeName) {
        ConfigurationSection section = helpersPlugin.getConfig().getConfigurationSection(nodeName);
        List<Location> spawnPoints = new ArrayList<>();
        if (section != null) {
            for (String id : section.getKeys(false)) {
                spawnPoints.add(section.getLocation(id));
            }
        }
        return spawnPoints;
    }

    public void addSpawnPoint(Location location) {
        spawnPointsAtSpawn.add(location);
        saveSpawnPoint(location, "spawnpoints");
    }

    public void addSpawnPointAtPit(Location location) {
        spawnPointsAtPit.add(location);
        saveSpawnPoint(location, "pitspawnpoints");
    }

    public void addSpawnPointAwayFromSpawn(Location location) {
        spawnPointsAwayFromSpawn.add(location);
        saveSpawnPoint(location, "remotespawnpoints");
    }

    public Location getRandomSpawnPoint() {
        return spawnPointsAtSpawn.get(RANDOM.nextInt(spawnPointsAtSpawn.size()));
    }

    public Location getRandomSpawnPointAtPit() {
        return spawnPointsAtPit.get(RANDOM.nextInt(spawnPointsAtPit.size()));
    }

    public Location getRandomSpawnPointAwayFromSpawn() {
        return spawnPointsAwayFromSpawn.get(RANDOM.nextInt(spawnPointsAwayFromSpawn.size()));
    }

    private Map<Player, Location> playerRespawns = new HashMap<>();

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.getDrops().clear();
        final Location spawnPoint;
        if (WorldGuardUtil.playerIsInRegion(player, "pit_main") || WorldGuardUtil.playerIsInRegion(player, "pit_tower")) {
            spawnPoint = getRandomSpawnPointAtPit();
        } else {
            spawnPoint = getRandomSpawnPoint();
        }
        playerRespawns.put(player, spawnPoint);
        helpersPlugin.getServer().getScheduler().scheduleSyncDelayedTask(helpersPlugin, () -> player.spigot().respawn(), 1L);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        event.setRespawnLocation(playerRespawns.get(player));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Location spawnPoint = getRandomSpawnPoint();
        player.teleport(spawnPoint);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        playerRespawns.remove(event.getPlayer());
    }

}
