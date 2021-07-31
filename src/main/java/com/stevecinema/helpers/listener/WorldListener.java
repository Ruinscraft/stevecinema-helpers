package com.stevecinema.helpers.listener;

import com.stevecinema.helpers.HelpersPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class WorldListener implements Listener {

    private HelpersPlugin helpersPlugin;

    public WorldListener(HelpersPlugin helpersPlugin) {
        this.helpersPlugin = helpersPlugin;
        helpersPlugin.getServer().getPluginManager().registerEvents(this, helpersPlugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!helpersPlugin.getAdminModeManager().isAdminMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (!helpersPlugin.getAdminModeManager().isAdminMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

    }

}
