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

import net.minecraft.util.math.BlockPos;

/**
 * Internal interface for Ritual Pedestals TE's<br>
 * This shouldn't be used by other mods.<br>
 * This interface exists in case I add more methods to the pedestals
 * @author shinoow
 *
 */
public interface IRitualPedestal extends ISingletonInventory {

	/**
	 * Returns the Ritual Altar this pedestal is attached to<br>
	 * (the BlockPos of the altar should be kept in the pedestal's NBT)
	 */
	IRitualAltar getAltar();

	/**
	 * Sets the Ritual Altar position
	 */
	void setAltar(BlockPos pos);

	/**
	 * Consumes the Item placed on the pedestal (if any)
	 */
	void consumeItem();
}
