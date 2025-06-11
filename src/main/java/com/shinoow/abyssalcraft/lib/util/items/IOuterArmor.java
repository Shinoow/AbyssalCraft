/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
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
	@Nullable String getOuterArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type);
}
