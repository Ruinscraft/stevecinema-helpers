package com.stevecinema.helpers.command.staff;

import com.stevecinema.helpers.command.RateLimitedCommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class AddPitSpawnPointCommand extends RateLimitedCommandExecutor {

    @Override
    public boolean onRateLimitedCommand(Player player, Command command, String label, String[] args) {
        return false;
    }

}
