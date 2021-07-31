package com.stevecinema.helpers.command.general;

import com.stevecinema.helpers.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RulesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatUtil.COLOR_2 + "View the server rules here: " + ChatUtil.COLOR_3 + "https://ruinscraft.com/servers/steve-cinema/rules");
        return true;
    }

}
