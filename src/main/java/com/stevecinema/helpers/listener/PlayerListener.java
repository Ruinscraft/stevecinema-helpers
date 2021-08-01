package com.stevecinema.helpers.listener;

import com.stevecinema.helpers.HelpersPlugin;
import com.stevecinema.helpers.util.WorldGuardUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {

    private HelpersPlugin helpersPlugin;

    public PlayerListener(HelpersPlugin helpersPlugin) {
        this.helpersPlugin = helpersPlugin;
        helpersPlugin.getServer().getPluginManager().registerEvents(this, helpersPlugin);
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setWalkSpeed(0.35f);
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        event.setJoinMessage(null);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        player.setWalkSpeed(0.35f);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!helpersPlugin.getAdminModeManager().isAdminMode(player)) {
            event.setCancelled(true);
        }

        // Allow pressure plates and buttons
        if (event.getClickedBlock() != null) {
            if (event.getAction() == Action.PHYSICAL) {
                if (event.getClickedBlock().getType().name().contains("PRESSURE")) {
                    event.setCancelled(false);
                }
            } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (event.getClickedBlock().getType().name().contains("BUTTON") || event.getClickedBlock().getType().name().contains("LEVER")) {
                    event.setCancelled(false);
                }
            }
        }
    }

    @EventHandler
    public void noFall(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled(true);
        }
    }

    // TODO: this is extremely temporary
    @EventHandler
    public void onPVP(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
        if (event.getDamager() instanceof Player player && event.getEntity() instanceof Player) {
            if (player.getInventory().contains(Material.GOLDEN_SWORD)) {
                boolean inPit = false;
                if (WorldGuardUtil.playerIsInRegion(player, "pit_main") || WorldGuardUtil.playerIsInRegion(player, "pit_tower")) {
                    inPit = true;
                }
                if (inPit) {
                    event.setCancelled(false);
                }
            }
        }
    }

}
