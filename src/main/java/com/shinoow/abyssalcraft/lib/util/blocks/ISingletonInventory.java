/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.lib.util.blocks;

import net.minecraft.item.ItemStack;

/**
 * TileEntity that can hold a single ItemStack? Look no further!
 * @author shinoow
 *
 */
public interface ISingletonInventory {

	/**
	 * Returns the ItemStack placed on the Altar, if any
	 */
	ItemStack getItem();

	/**
	 * Sets the ItemStack placed on the Altar
	 * @param item ItemStack to place on the Altar
	 */
	void setItem(ItemStack item);

	/**
	 * Returns whether or not the placed Item should rotate
	 */
	default boolean shouldItemRotate() {
		return true;
	}
}
