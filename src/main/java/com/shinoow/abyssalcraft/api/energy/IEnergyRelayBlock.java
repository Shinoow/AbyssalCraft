/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.energy;

/**
 * Interface to use on blocks for storing metadata used in the item
 * representation of the block (Energy Relay version)
 *
 * @author shinoow
 *
 * @since 1.7.5
 */
public interface IEnergyRelayBlock extends IEnergyBlock {

	/**
	 * Returns the distance an Energy Relay can transfer Potential Energy
	 */
	int getRange();
}
