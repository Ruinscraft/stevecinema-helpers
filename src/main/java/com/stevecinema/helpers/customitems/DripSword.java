package com.stevecinema.helpers.customitems;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class DripSword extends CustomItem implements Listener {

    private static ItemStack itemStack;

    static {
        itemStack = new ItemStack(Material.GOLDEN_SWORD, 1);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("DRIP SWORD");
        meta.setUnbreakable(true);
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 4, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
        itemStack.setItemMeta(meta);
    }

    public DripSword() {
        super(itemStack);
    }

}
