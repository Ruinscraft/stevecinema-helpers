package com.stevecinema.helpers;

import com.stevecinema.helpers.storage.PlayerStatsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerStatsListener implements Listener {

    private PlayerStatsManager playerStatsManager;

    public PlayerStatsListener(PlayerStatsManager playerStatsManager) {
        this.playerStatsManager = playerStatsManager;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player killed = event.getEntity();
        Player killer = event.getEntity().getKiller();
        playerStatsManager.getStats(killed).addDeath();
        if (killer != null) {
            playerStatsManager.getStats(killer).addKill();
        }
    }

}
