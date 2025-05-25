package com.shinoow.abyssalcraft.lib.util.items;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Adds support for a second layer on a set of armor<br>
 * (similar to how the player skin has a second layer)
 * @author shinoow
 *
 */
public interface IOuterArmor {

	/**
	 * Version of {@link Item#getArmorTexture(ItemStack, Entity, EntityEquipmentSlot, String)}
	 * for a texture to the "second" armor layer
	 */
	@Nullable
	public String getOuterArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type);
}
