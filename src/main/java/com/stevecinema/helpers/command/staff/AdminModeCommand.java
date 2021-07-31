package com.stevecinema.helpers.command.staff;

import com.stevecinema.helpers.AdminModeManager;
import com.stevecinema.helpers.util.ChatUtil;
import com.stevecinema.helpers.command.RateLimitedCommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class AdminModeCommand extends RateLimitedCommandExecutor {

    private AdminModeManager adminModeManager;

    public AdminModeCommand(AdminModeManager adminModeManager) {
        this.adminModeManager = adminModeManager;
    }

    @Override
    public boolean onRateLimitedCommand(Player player, Command command, String label, String[] args) {
        if (adminModeManager.isAdminMode(player)) {
            player.sendMessage(ChatUtil.COLOR_2 + "Admin mode disabled");
            adminModeManager.setAdminMode(player, false);
        } else {
            player.sendMessage(ChatUtil.COLOR_2 + "Admin mode enabled");
            adminModeManager.setAdminMode(player, true);
        }

        return true;
    }

}
