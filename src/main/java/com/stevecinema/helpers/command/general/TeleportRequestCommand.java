package com.stevecinema.helpers.command.general;

import com.stevecinema.helpers.HelpersPlugin;
import com.stevecinema.helpers.command.RateLimitedCommandExecutor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class TeleportRequestCommand extends RateLimitedCommandExecutor {

    private HelpersPlugin helpersPlugin;
    private Map<Player, Player> requests;

    public TeleportRequestCommand(HelpersPlugin helpersPlugin) {
        this.helpersPlugin = helpersPlugin;
        requests = new ConcurrentHashMap<>();
    }

    @Override
    public boolean onRateLimitedCommand(Player player, Command command, String label, String[] args) {
        if (label.startsWith("tpa")) {
            if (!requests.containsKey(player)) {
                player.sendMessage(ChatColor.RED + "No teleport request pending");
                return true;
            }
            requests.remove(player).teleport(player);
        } else {
            if (args.length < 1) {
                player.sendMessage(ChatColor.RED + "/" + label + " <target>");
                return true;
            }
            Optional<Player> target = helpersPlugin.getServer().matchPlayer(args[0]).stream().findFirst();
            if (target.isEmpty()) {
                player.sendMessage(ChatColor.RED + "Could not find " + args[0]);
                return true;
            }
            requests.put(target.get(), player);
            long oneMinuteInTicks = 20L * 60;
            helpersPlugin.getServer().getScheduler().runTaskLater(helpersPlugin, () -> requests.remove(target.get()), oneMinuteInTicks);
            target.get().sendMessage(ChatColor.GOLD + player.getName() + " is requesting to teleport to you. Accept with /tpa");
            player.sendMessage(ChatColor.GOLD + "Requesting to teleport to " + target.get().getName());
        }
        return true;
    }

}
