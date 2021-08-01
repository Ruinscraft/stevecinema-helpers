package com.stevecinema.helpers.listener;

import com.stevecinema.helpers.HelpersPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerListener implements Listener {

    public PlayerListener(HelpersPlugin helpersPlugin) {
        helpersPlugin.getServer().getPluginManager().registerEvents(this, helpersPlugin);
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

}
