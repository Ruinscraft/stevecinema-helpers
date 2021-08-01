package com.stevecinema.helpers;

import com.stevecinema.helpers.customitems.DripSword;
import com.stevecinema.helpers.util.WorldGuardUtil;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class PitManager {

    private static DripSword DRIP_SWORD = new DripSword();

    private Set<Player> previousPitPlayers;
    private HelpersPlugin helpersPlugin;

    public PitManager(HelpersPlugin helpersPlugin) {
        this.helpersPlugin = helpersPlugin;
        previousPitPlayers = new HashSet<>();
        helpersPlugin.getServer().getScheduler().runTaskTimer(helpersPlugin, new PitCheckerRunnable(), 10L, 10L);
    }

    private class PitCheckerRunnable implements Runnable {
        @Override
        public void run() {
            Set<Player> pitPlayers = new HashSet<>();
            World world = helpersPlugin.getServer().getWorlds().get(0);
            for (Player player : WorldGuardUtil.getPlayersInRegion(world, "pit_main")) {
                if (player.getLocation().getBlockY() <= 63) {
                    pitPlayers.add(player);
                }
            }
            pitPlayers.addAll(WorldGuardUtil.getPlayersInRegion(world, "pit_tower"));
            pitPlayers.addAll(WorldGuardUtil.getPlayersInRegion(world, "pit_tower_base"));
            for (Player pitPlayer : pitPlayers) {
                if (!pitPlayer.getInventory().contains(DRIP_SWORD.getItemStack())) {
                    DRIP_SWORD.giveToPlayer(pitPlayer);
                }
            }
            for (Player previousPitPlayer : previousPitPlayers) {
                if (!pitPlayers.contains(previousPitPlayer)) {
                    previousPitPlayer.getInventory().remove(DRIP_SWORD.getItemStack());
                }
            }
            previousPitPlayers = pitPlayers;
        }
    }

}
