/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api;

import com.shinoow.abyssalcraft.api.item.ICrystal;

import net.minecraft.item.ItemStack;

/**
 * Utilities for the AbyssalCraft API
 * @author shinoow
 *
 * @since 1.4
 */
public class APIUtils {

	/**
	 * Checks if the ItemStack is a Crystal
	 * @param item ItemStack to check
	 * @return True if the ItemStack is a Crystal, otherwise false
	 * 
	 * @since 1.4
	 */
	public static boolean isCrystal(ItemStack item){
		if(item.getItem() instanceof ICrystal)
			return true;
		for(ItemStack crystal: AbyssalCraftAPI.getCrystals())
			return crystal.isItemEqual(item);
		return false;
	}
}
