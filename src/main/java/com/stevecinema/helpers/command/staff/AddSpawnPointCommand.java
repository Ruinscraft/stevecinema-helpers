package com.stevecinema.helpers.command.staff;

import com.stevecinema.helpers.HelpersPlugin;
import com.stevecinema.helpers.command.RateLimitedCommandExecutor;
import com.stevecinema.helpers.spawning.SpawnPoint;
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
            player.sendMessage(ChatColor.RED + "/" + label + " <awayfromspawn true/false>");
            return true;
        }

        Location location = player.getLocation();
        boolean awayFromSpawn = args[0].equals("true");
        SpawnPoint spawnPoint = new SpawnPoint(location, awayFromSpawn);



        return true;
    }

}
