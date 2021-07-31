package com.stevecinema.helpers.command.general;

import com.stevecinema.helpers.util.ChatUtil;
import com.stevecinema.helpers.command.RateLimitedCommandExecutor;
import com.stevecinema.helpers.storage.PlayerStats;
import com.stevecinema.helpers.storage.PlayerStatsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class ShowStatsCommand extends RateLimitedCommandExecutor {

    private PlayerStatsManager playerStatsManager;

    public ShowStatsCommand(PlayerStatsManager playerStatsManager) {
        this.playerStatsManager = playerStatsManager;
    }

    @Override
    public boolean onRateLimitedCommand(Player player, Command command, String label, String[] args) {
        Player target;

        if (args.length < 1) {
            target = player;
        } else {
            target = Bukkit.getPlayer(args[0]);
        }

        if (target == null) {
            player.sendMessage(ChatColor.RED + args[0] + " is not online.");
            return true;
        }

        PlayerStats playerStats = playerStatsManager.getStats(target);
        int playtimeHours = playerStats.getPlayMinutes() / 60;
        String message = ChatUtil.COLOR_3 + "[STATS] " + ChatUtil.COLOR_2 + player.getName() +
                " :: kills=" +
                playerStats.getKills() +
                " : deaths=" +
                playerStats.getDeaths() +
                " : playtime=" +
                playtimeHours + "hours";
        player.sendMessage(message);

        return true;
    }

}
