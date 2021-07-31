package com.stevecinema.helpers.command.general;

import com.stevecinema.helpers.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatUtil.COLOR_2 + "View info on how to play the server here: " + ChatUtil.COLOR_3 + " https://ruinscraft.com/servers/steve-cinema");
        return true;
    }

}
