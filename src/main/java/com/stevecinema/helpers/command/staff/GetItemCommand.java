package com.stevecinema.helpers.command.staff;

import com.stevecinema.helpers.command.RateLimitedCommandExecutor;
import com.stevecinema.helpers.customitems.DripSword;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class GetItemCommand extends RateLimitedCommandExecutor {

    @Override
    public boolean onRateLimitedCommand(Player player, Command command, String label, String[] args) {
        DripSword dripSword = new DripSword();
        dripSword.giveToPlayer(player);
        return false;
    }

}
