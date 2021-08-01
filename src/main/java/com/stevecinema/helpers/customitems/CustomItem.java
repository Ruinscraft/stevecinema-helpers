package com.stevecinema.helpers.customitems;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomItem {

    private ItemStack itemStack;

    public CustomItem(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void giveToPlayer(Player player) {
        player.getInventory().addItem(itemStack);
    }

}
