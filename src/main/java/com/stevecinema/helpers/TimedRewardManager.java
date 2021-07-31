package com.stevecinema.helpers;

import com.stevecinema.helpers.util.VaultUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TimedRewardManager implements Listener {

    private HelpersPlugin helpersPlugin;
    private Map<Player, Long> lastRewardTimes;
    private Map<Player, Integer> spmCache;

    public TimedRewardManager(HelpersPlugin helpersPlugin) {
        this.helpersPlugin = helpersPlugin;
        helpersPlugin.getServer().getPluginManager().registerEvents(this, helpersPlugin);
        long oneMinuteTicks = 20L * 60;
        helpersPlugin.getServer().getScheduler().runTaskTimer(helpersPlugin, new TimedRewardRunnable(), 20L, oneMinuteTicks);
        lastRewardTimes = new ConcurrentHashMap<>();
        spmCache = new ConcurrentHashMap<>();
    }

    public int getSPM(Player player) {
        return spmCache.getOrDefault(player, 0);
    }

    private class TimedRewardRunnable implements Runnable {
        @Override
        public void run() {
            for (Player player : helpersPlugin.getServer().getOnlinePlayers()) {
                handleReward(player);
            }
        }

        private void handleReward(Player player) {
            long now = System.currentTimeMillis();
            int spm = calculateSPM(player);

            if (lastRewardTimes.containsKey(player)) {
                long msSinceLastReward = now - lastRewardTimes.get(player);
                double silverPerMs = spm / 60D / 1000D;
                int owed = (int) Math.floor(msSinceLastReward * silverPerMs);
                VaultUtil.deposit(player, owed);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Silver per minute: " + spm));
            }

            lastRewardTimes.put(player, now);
            spmCache.put(player, spm);
        }

        private int calculateSPM(Player player) {
            int currentSilver = VaultUtil.getBalance(player);
            int spm = (currentSilver / 2500) + 20; // TODO: add additional multipliers
            return spm;
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        lastRewardTimes.remove(player);
        spmCache.remove(player);
    }

}
