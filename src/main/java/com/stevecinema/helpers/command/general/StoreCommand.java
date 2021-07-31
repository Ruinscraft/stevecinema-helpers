package com.stevecinema.helpers.command.general;

import com.stevecinema.helpers.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StoreCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatUtil.COLOR_2 + "Sponsor perks for Steve Cinema coming soon.");
        sender.sendMessage(ChatUtil.COLOR_2 + "You can visit our store at: " + ChatUtil.COLOR_3 + "https://store.ruinscraft.com");
        return true;
    }

}
