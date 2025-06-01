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
package com.shinoow.abyssalcraft.common.util;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class ArmorUtil {

	/**
	 * Checks if the target has a helmet with resistance to the source
	 * @param target Entity about to be attacked
	 * @param source Probably a plague or other damage source
	 * @return Whether or not the target has resistance against the source
	 */
	public static boolean hasHelmetWithResistance(EntityLivingBase target, DamageSource source) {

		ItemStack helmet = target.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

		if(!helmet.isEmpty())
			if(source == AbyssalCraftAPI.coralium) {
				if(helmet.getItem() == ACItems.plated_coralium_helmet ||
						helmet.getItem() == ACItems.depths_helmet ||
						helmet.getItem() == ACItems.ethaxium_helmet)
					return true;
			}
			else if(source == AbyssalCraftAPI.dread)
				if(helmet.getItem() == ACItems.dreadium_samurai_helmet
				|| helmet.getItem() == ACItems.ethaxium_helmet)
					return true;

		return false;
	}
}
