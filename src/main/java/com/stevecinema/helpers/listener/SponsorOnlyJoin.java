package com.stevecinema.helpers.listener;

import com.stevecinema.helpers.HelpersPlugin;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

public class SponsorOnlyJoin implements Listener {

    private LuckPerms luckPerms;

    public SponsorOnlyJoin(HelpersPlugin helpersPlugin) {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }
        helpersPlugin.getServer().getPluginManager().registerEvents(this, helpersPlugin);
    }

    @EventHandler
    public void onPreJoin(AsyncPlayerPreLoginEvent event) {
        boolean canJoin = false;
        User user = luckPerms.getUserManager().getUser(event.getUniqueId());

        for (Node node : user.getNodes()) {
            if (node instanceof PermissionNode permissionNode) {
                if (permissionNode.getPermission().contains("group.sponsor")) {
                    canJoin = true;
                }
            }
        }

        if (!canJoin) {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            event.setKickMessage("Steve Cinema is currently in Sponsor-only BETA");
        }
    }

}
