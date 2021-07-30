package com.stevecinema.helpers;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.Set;

public class AdminModeManager implements Listener {

    private Set<Player> adminModeUsers;

    public AdminModeManager(HelpersPlugin helpersPlugin) {
        helpersPlugin.getServer().getPluginManager().registerEvents(this, helpersPlugin);
        adminModeUsers = new HashSet<>();

        helpersPlugin.getServer().getScheduler().runTaskTimer(helpersPlugin, () -> {
            for (Player player : adminModeUsers) {
                if (!player.isOnline()) {
                    continue;
                }

                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Admin mode enabled"));
            }
        }, 10L, 10L);

    }

    public boolean isAdminMode(Player player) {
        return adminModeUsers.contains(player);
    }

    public void setAdminMode(Player player, boolean enable) {
        if (enable) {
            adminModeUsers.add(player);
        } else {
            adminModeUsers.remove(player);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        setAdminMode(event.getPlayer(), false);
    }

}
