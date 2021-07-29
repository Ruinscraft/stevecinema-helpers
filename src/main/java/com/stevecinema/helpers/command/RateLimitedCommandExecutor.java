package com.stevecinema.helpers.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public abstract class RateLimitedCommandExecutor implements CommandExecutor {

    private Map<UUID, Long> recentUsers;

    public RateLimitedCommandExecutor() {
        recentUsers = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if (recentUsers.containsKey(player.getUniqueId())) {
            long lastUse = recentUsers.get(player.getUniqueId());
            if (lastUse + TimeUnit.SECONDS.toMillis(30) > System.currentTimeMillis()) {
                player.sendMessage(ChatColor.RED + "Wait to use this command again.");
                return true;
            }
        }

        recentUsers.put(player.getUniqueId(), System.currentTimeMillis());

        onRateLimitedCommand(sender, command, label, args);

        return true;
    }


    public abstract boolean onRateLimitedCommand(CommandSender sender, Command command, String label, String[] args);

}
