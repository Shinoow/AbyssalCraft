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
package com.shinoow.abyssalcraft.lib.world.biome;

import java.util.List;

import net.minecraft.world.biome.Biome.SpawnListEntry;

/**
 * Utility interface for providing alternate spawn lists
 * @author shinoow
 *
 */
public interface IAlternateSpawnList {

	/**
	 * Returns the Abyssal Wasteland spawn list
	 */
	List<SpawnListEntry> getAbyssalWastelandList();

	/**
	 * Returns the Dreadlands spawn list
	 */
	List<SpawnListEntry> getDreadlandsList();
}
