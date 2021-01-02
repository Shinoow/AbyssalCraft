/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2021 Shinoow.
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
	 */
	void addEnergy(float energy);

	/**
	 * Consumes (removes) Potential Energy from the tile entity
	 * @param energy Energy quota to consume
	 * @return The amount of energy consumed
	 */
	float consumeEnergy(float energy);

	/**
	 * Returns whether or not this container can accept Potential Energy<br>
	 * (eg. if it  has room for more Potential Energy, or if it accepts external input at all)
	 */
	boolean canAcceptPE();

	/**
	 * Returns whether or not this container can transfer Potential Energy<br>
	 * (eg. if it has any Potential Energy stored that it can transfer, or if it allows extraction at all)
	 */
	boolean canTransferPE();

	/**
	 * Returns the TileEntity this interface is bound to
	 */
	TileEntity getContainerTile();
}
