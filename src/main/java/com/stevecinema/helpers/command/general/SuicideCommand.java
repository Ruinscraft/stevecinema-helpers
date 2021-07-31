package com.stevecinema.helpers.command.general;

import com.stevecinema.helpers.command.RateLimitedCommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class SuicideCommand extends RateLimitedCommandExecutor {

    @Override
    public boolean onRateLimitedCommand(Player player, Command command, String label, String[] args) {
        player.setHealth(0.0f);
        return true;
    }

}
