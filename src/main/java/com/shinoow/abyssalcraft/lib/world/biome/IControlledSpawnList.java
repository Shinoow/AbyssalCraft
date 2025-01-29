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
package com.shinoow.abyssalcraft.lib.world.biome;

/**
 * Utility interface for controlled biome spawn list
 * @author shinoow
 *
 */
public interface IControlledSpawnList {

	/**
	 * Assigns mob spawns (can also wipe unwanted mobs, check impl)
	 */
	void setMobSpawns();
}
