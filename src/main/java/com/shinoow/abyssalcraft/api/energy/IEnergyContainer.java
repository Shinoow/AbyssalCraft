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

import net.minecraft.tileentity.TileEntity;

/**
 * Interface to use on tile entities that can hold Potential Energy
 *
 * @author shinoow
 *
 * @since 1.4.5
 */
public interface IEnergyContainer {

	/**
	 * Gets the Potential Energy contained within the tile entity
	 */
	float getContainedEnergy();

	/**
	 * Gets the maximum Potential Energy the tile entity can hold
	 */
	int getMaxEnergy();

	/**
	 * Adds Potential Energy to the tile entity
	 * @param energy Energy quota to add
	 * @return Energy overflow, if any
	 */
	default float addEnergy(float energy) {
		return PEUtils.addEnergy(this, energy);
	}

	/**
	 * Consumes (removes) Potential Energy from the tile entity
	 * @param energy Energy quota to consume
	 * @return The amount of energy consumed
	 */
	default float consumeEnergy(float energy) {
		return PEUtils.consumeEnergy(this, energy);
	}

	/**
	 * Sets the Potential Energy of the tile entity to the amount
	 * @param energy Energy amount to set
	 */
	void setEnergy(float energy);

	/**
	 * Returns whether or not this container can accept Potential Energy<br>
	 * (eg. if it  has room for more Potential Energy, or if it accepts external input at all)
	 */
	default boolean canAcceptPE() {
		return getContainedEnergy() < getMaxEnergy();
	}

	/**
	 * Returns whether or not this container can transfer Potential Energy<br>
	 * (eg. if it has any Potential Energy stored that it can transfer, or if it allows extraction at all)
	 */
	default boolean canTransferPE() {
		return getContainedEnergy() > 0;
	}

	/**
	 * Returns the TileEntity this interface is bound to
	 */
	TileEntity getContainerTile();
}
