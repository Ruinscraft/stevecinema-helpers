package com.stevecinema.helpers.command.general;

import com.stevecinema.helpers.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class VoteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatUtil.COLOR_2 + "[AVAILABLE SOON]");
        sender.sendMessage(ChatUtil.COLOR_2 + "You can vote for silver daily here: " + ChatUtil.COLOR_3 + " https://ruinscraft.com/servers/steve-cinema");
        return true;
    }

}
