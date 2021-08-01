package com.stevecinema.helpers.command.staff;

import com.stevecinema.helpers.HelpersPlugin;
import com.stevecinema.helpers.command.RateLimitedCommandExecutor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class AddSpawnPointCommand extends RateLimitedCommandExecutor {

    private HelpersPlugin helpersPlugin;

    public AddSpawnPointCommand(HelpersPlugin helpersPlugin) {
        this.helpersPlugin = helpersPlugin;
    }

    @Override
    public boolean onRateLimitedCommand(Player player, Command command, String label, String[] args) {
        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "/" + label + " <spawn/pit/remote>");
            return true;
        }

        Location location = player.getLocation();

        boolean success = false;

        switch (args[0]) {
            case "spawn" -> {
                helpersPlugin.getSpawnPointManager().addSpawnPoint(location);
                success = true;
            }
            case "pit" -> {
                helpersPlugin.getSpawnPointManager().addSpawnPointAtPit(location);
                success = true;
            }
            case "remote" ->  {
                helpersPlugin.getSpawnPointManager().addSpawnPointAwayFromSpawn(location);
                success = true;
            }
        }

        if (success) {
            player.sendMessage(ChatColor.GOLD + "Added spawn point");
        } else {
            player.sendMessage(ChatColor.RED + "Invalid spawn point type");
        }

        return true;
    }

}
