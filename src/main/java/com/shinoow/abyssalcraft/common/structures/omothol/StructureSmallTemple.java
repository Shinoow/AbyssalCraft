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
package com.shinoow.abyssalcraft.common.structures.omothol;

import java.util.Random;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.shinoow.abyssalcraft.common.structures.IOmotholBuilding;
import com.shinoow.abyssalcraft.common.structures.StructureData;

public class StructureSmallTemple extends WorldGenerator implements IOmotholBuilding {

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		return false;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos, EnumFacing face) {

		switch(face){
		case EAST:
			break;
		case NORTH:
			break;
		case SOUTH:
			break;
		case WEST:
			break;
		default:
			break;

		}

		return false;
	}

	@Override
	public StructureData getStructureData(EnumFacing face) {

		switch(face){
		case EAST:
			return new StructureData(-5, 0, -4, 5, 10, 5);
		case NORTH:
			return new StructureData(-4, 0, -5, 5, 10, 5);
		case SOUTH:
			return new StructureData(-5, 0, -5, 4, 10, 5);
		case WEST:
			return new StructureData(-5, 0, -5, 4, 10, 5);
		default:
			return null;
		}
	}
}
