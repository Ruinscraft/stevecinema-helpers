package com.stevecinema.helpers.command.general;

import com.stevecinema.helpers.command.RateLimitedCommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SuicideCommand extends RateLimitedCommandExecutor {

    @Override
    public boolean onRateLimitedCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }

}
