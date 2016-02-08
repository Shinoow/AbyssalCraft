/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.structures;

import java.util.Random;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Implement on any building generated in Omothol
 * @author shinoow
 *
 */
public interface IOmotholBuilding {

	/**
	 * Custom generate method, works like the regular one does, with the addition of a EnumFacing param
	 */
	public boolean generate(World world, Random rand, BlockPos pos, EnumFacing face);

	/**
	 * Used to fetch Structure Data for the building
	 * @param dir Current direction
	 */
	public StructureData getStructureData(EnumFacing face);
}
