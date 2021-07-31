package com.stevecinema.helpers.command.general;

import com.stevecinema.helpers.util.ChatUtil;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UptimeCommand implements CommandExecutor {

    private static long startupTime;

    static {
        startupTime = System.currentTimeMillis();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        long diff = System.currentTimeMillis() - startupTime;
        sender.sendMessage(ChatUtil.COLOR_2 + "The server has been up for: " + ChatUtil.COLOR_3 + DurationFormatUtils.formatDurationWords(diff, true, true));
        return true;
    }

}
