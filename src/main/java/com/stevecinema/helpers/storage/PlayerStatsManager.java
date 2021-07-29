package com.stevecinema.helpers.storage;

import com.stevecinema.helpers.HelpersPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerStatsManager implements Listener {

    private HelpersPlugin helpersPlugin;
    private Map<UUID, PlayerStats> statsCache;

    public PlayerStatsManager(HelpersPlugin helpersPlugin) {
        this.helpersPlugin = helpersPlugin;

        statsCache = new ConcurrentHashMap<>();

        // Save cache every minute
        long oneMinuteTicks = 20L * 60;
        helpersPlugin.getServer().getScheduler().runTaskTimerAsynchronously(helpersPlugin, new PlayerStatsSaveRunnable(), oneMinuteTicks, oneMinuteTicks);
    }

    public PlayerStats getStats(Player player) {
        return statsCache.get(player.getUniqueId());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        helpersPlugin.getPlayerStatsStorage().loadPlayerStats(player.getUniqueId()).thenAccept(playerStats -> {
            if (player.isOnline()) {
                statsCache.put(player.getUniqueId(), playerStats);
            }
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerStats removed = statsCache.remove(player.getUniqueId());
        helpersPlugin.getPlayerStatsStorage().savePlayerStats(removed);
    }

    private class PlayerStatsSaveRunnable implements Runnable {
        @Override
        public void run() {
            List<PlayerStats> toSaveBatch = statsCache.values().stream().toList();
            helpersPlugin.getPlayerStatsStorage().savePlayerStatsBatch(toSaveBatch);
        }
    }

}
