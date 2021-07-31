package com.stevecinema.helpers.command.staff;

import com.stevecinema.helpers.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BroadcastCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "/" + label + " <msg>");
        } else {
            String message = String.join(" ", args);
            Bukkit.broadcastMessage(ChatUtil.COLOR_1 + "[ALERT] " + ChatUtil.COLOR_2 + message);
        }
        return true;
    }

}
