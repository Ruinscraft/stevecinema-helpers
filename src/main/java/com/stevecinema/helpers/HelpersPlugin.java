package com.stevecinema.helpers;

import com.stevecinema.helpers.command.general.*;
import com.stevecinema.helpers.command.staff.AdminModeCommand;
import com.stevecinema.helpers.command.staff.BroadcastCommand;
import com.stevecinema.helpers.listener.PlayerStatsListener;
import com.stevecinema.helpers.listener.WorldListener;
import com.stevecinema.helpers.storage.PlayerStatsManager;
import com.stevecinema.helpers.storage.PlayerStatsStorage;
import com.stevecinema.helpers.storage.sql.UnpooledMySQLPlayerStatsStorage;
import com.stevecinema.helpers.util.VaultUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class HelpersPlugin extends JavaPlugin {

    private PlayerStatsStorage playerStatsStorage;
    private PlayerStatsManager playerStatsManager;
    private AdminModeManager adminModeManager;
    private TimedRewardManager timedRewardManager;

    public PlayerStatsStorage getPlayerStatsStorage() {
        return playerStatsStorage;
    }

    public PlayerStatsManager getPlayerStatsManager() {
        return playerStatsManager;
    }

    public AdminModeManager getAdminModeManager() {
        return adminModeManager;
    }

    public TimedRewardManager getTimedRewardManager() {
        return timedRewardManager;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        String mysqlHost = getConfig().getString("storage.mysql.host");
        int mysqlPort = getConfig().getInt("storage.mysql.port");
        String mysqlDatabase = getConfig().getString("storage.mysql.database");
        String mysqlUsername = getConfig().getString("storage.mysql.username");
        String mysqlPassword = getConfig().getString("storage.mysql.password");

        try {
            playerStatsStorage = new UnpooledMySQLPlayerStatsStorage(mysqlHost, mysqlPort, mysqlDatabase, mysqlUsername, mysqlPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (playerStatsStorage == null) {
            getLogger().info("PlayerStats storage not enabled");
        } else {
            playerStatsManager = new PlayerStatsManager(this);
            getServer().getPluginManager().registerEvents(new PlayerStatsListener(playerStatsManager), this);
            getCommand("showstats").setExecutor(new ShowStatsCommand(playerStatsManager));
        }

        adminModeManager = new AdminModeManager(this);
        timedRewardManager = new TimedRewardManager(this);

        new WorldListener(this);

        getCommand("vote").setExecutor(new VoteCommand());
        getCommand("rules").setExecutor(new RulesCommand());
        getCommand("help").setExecutor(new HelpCommand());
        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("uptime").setExecutor(new UptimeCommand());
        getCommand("store").setExecutor(new StoreCommand());

        // Staff
        getCommand("adminmode").setExecutor(new AdminModeCommand(adminModeManager));
        getCommand("broadcast").setExecutor(new BroadcastCommand());

        if (!VaultUtil.init(this)) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    @Override
    public void onDisable() {

    }

}
