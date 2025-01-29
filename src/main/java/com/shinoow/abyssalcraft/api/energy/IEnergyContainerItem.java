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
 * Interface to use on items that can hold Potential Energy
 *
 * @author shinoow
 *
 * @since 1.7.5
 */
public interface IEnergyContainerItem {

	/**
	 * Gets the Potential Energy contained within the item
	 * @param stack ItemStack containing the item
	 */
	default float getContainedEnergy(ItemStack stack) {
		return PEUtils.getContainedEnergy(stack);
	}

	/**
	 * Gets the maximum Potential Energy the item can hold
	 * @param stack ItemStack containing the item
	 */
	int getMaxEnergy(ItemStack stack);

	/**
	 * Adds Potential Energy to the item
	 * @param stack ItemStack containing the item
	 * @param energy Energy quanta to add
	 * @return Energy overflow, if any
	 */
	default float addEnergy(ItemStack stack, float energy) {
		return PEUtils.addEnergy(this, stack, energy);
	}

	/**
	 * Consumes (removes) Potential Energy from the item
	 * @param stack ItemStack containing the item
	 * @param energy Energy quanta to consume
	 * @return The amount of energy consumed
	 */
	default float consumeEnergy(ItemStack stack, float energy) {
		return PEUtils.consumeEnergy(stack, energy);
	}

	/**
	 * Returns Whether or not this item can accept Potential Energy<br>
	 * (eg. if it  has room for more Potential Energy, or if it accepts input at all)
	 * @param stack ItemStack containing the item
	 */
	default boolean canAcceptPE(ItemStack stack) {
		return getContainedEnergy(stack) < getMaxEnergy(stack);
	}

	/**
	 * Returns Whether or not this item can transfer Potential Energy<br>
	 * (eg. if it has any Potential Energy stored that it can transfer, or if it allows extraction at all)
	 * @param stack ItemStack containing the item
	 */
	default boolean canTransferPE(ItemStack stack) {
		return getContainedEnergy(stack) > 0;
	}
}
