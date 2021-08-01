package com.stevecinema.helpers.util;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public final class WorldGuardUtil {

    private static WorldGuard worldGuard;

    public static WorldGuard getWorldGuard() {
        if (worldGuard == null) {
            worldGuard = WorldGuard.getInstance();
        }

        return worldGuard;
    }

    public static Set<Player> getPlayersInRegion(ProtectedRegion region) {
        Set<Player> players = new HashSet<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            Location location = player.getLocation();
            if (region.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
                players.add(player);
            }
        }
        return players;
    }

    public static boolean playerInPit(Player player) {
        World wgWorld = BukkitAdapter.adapt(player.getWorld());
        for (String regionId : worldGuard.getPlatform().getRegionContainer().get(wgWorld).getRegions().keySet()) {
            if (regionId.startsWith("pit")) {
                return true;
            }
        }
        return false;
    }

}
