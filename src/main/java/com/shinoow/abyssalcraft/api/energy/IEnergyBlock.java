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
package com.shinoow.abyssalcraft.api.energy;

import net.minecraft.item.ItemStack;

/**
 * Interface to use on blocks for storing metadata used in the item
 * representation of the block
 *
 * @author shinoow
 *
 * @since 2.0.0
 */
public interface IEnergyBlock {

	/**
	 * Returns whether or not the item representation of this block
	 * can accept Potential Energy
	 */
	default boolean canAcceptPE() {
		return true;
	}

	/**
	 * Returns whether or not the item representation of this block
	 * can transfer Potential Energy
	 */
	default boolean canTransferPE() {
		return true;
	}

	/**
	 * Gets the maximum Potential Energy the item representation
	 * of this block can hold
	 * @param stack ItemStack containing the Block
	 */
	int getMaxEnergy(ItemStack stack);
}
