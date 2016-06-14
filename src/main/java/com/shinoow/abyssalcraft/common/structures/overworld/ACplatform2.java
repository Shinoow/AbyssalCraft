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
package com.shinoow.abyssalcraft.common.structures.overworld;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.shinoow.abyssalcraft.api.block.ACBlocks;

public class ACplatform2 extends WorldGenerator
{
	protected IBlockState[] GetValidSpawnBlocks() {
		return new IBlockState[] {
				ACBlocks.darkstone.getDefaultState(),
				ACBlocks.darklands_grass.getDefaultState()
		};
	}

	public boolean LocationIsValidSpawn(World world, BlockPos pos){
		int distanceToAir = 0;
		IBlockState checkID = world.getBlockState(pos);

		while (checkID != Blocks.air.getDefaultState()){
			distanceToAir++;
			checkID = world.getBlockState(pos.up(distanceToAir));
		}

		if (distanceToAir > 1)
			return false;
		pos.up(distanceToAir - 1);

		IBlockState blockID = world.getBlockState(pos);
		IBlockState blockIDAbove = world.getBlockState(pos.up(1));
		IBlockState blockIDBelow = world.getBlockState(pos.down(1));
		for (IBlockState x : GetValidSpawnBlocks()){
			if (blockIDAbove != Blocks.air.getDefaultState())
				return false;
			if (blockID == x)
				return true;
			else if (blockID == Blocks.snow.getDefaultState() && blockIDBelow == x)
				return true;
		}
		return false;
	}

	public ACplatform2() { }

	@Override
	public boolean generate(World world, Random random, BlockPos pos) {

		if(!LocationIsValidSpawn(world, pos) || !LocationIsValidSpawn(world, pos.east(7)) || !LocationIsValidSpawn(world, pos.add(7, 0, 8)) || !LocationIsValidSpawn(world, pos.south(8)))
			return false;

		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();

		world.setBlockState(new BlockPos(i + 0, j + 0, k + 0), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 0, j + 0, k + 1), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 0, j + 0, k + 2), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 0, j + 0, k + 3), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 0, j + 0, k + 4), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 0, j + 0, k + 5), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 0, j + 0, k + 6), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 0, j + 1, k + 0), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 0, j + 1, k + 1), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 0, j + 1, k + 2), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 0, j + 1, k + 3), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 0, j + 1, k + 4), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 0, j + 1, k + 5), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 0, j + 1, k + 6), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 0, j + 2, k + 0), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 0, j + 2, k + 1), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 0, j + 2, k + 2), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 0, j + 2, k + 3), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 0, j + 2, k + 4), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 0, j + 2, k + 5), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 0, j + 2, k + 6), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 1, j + 0, k + 0), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 1, j + 0, k + 1), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 1, j + 0, k + 2), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 1, j + 0, k + 3), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 1, j + 0, k + 4), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 1, j + 0, k + 5), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 1, j + 0, k + 6), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 1, j + 1, k + 0), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 1, j + 1, k + 2), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 1, j + 1, k + 3), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 1, j + 1, k + 4), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 1, j + 1, k + 6), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 1, j + 2, k + 0), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 1, j + 2, k + 1), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 1, j + 2, k + 2), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 1, j + 2, k + 3), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 1, j + 2, k + 4), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 1, j + 2, k + 5), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 1, j + 2, k + 6), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 0, k + 0), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 0, k + 1), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 0, k + 2), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 0, k + 3), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 0, k + 4), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 0, k + 5), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 0, k + 6), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 1, k + 0), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 1, k + 1), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 1, k + 2), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 1, k + 3), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 1, k + 4), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 1, k + 5), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 1, k + 6), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 2, k + 0), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 2, k + 1), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 2, k + 2), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 2, k + 3), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 2, k + 4), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 2, k + 5), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 2, j + 2, k + 6), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 0, k + 0), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 0, k + 1), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 0, k + 2), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 0, k + 3), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 0, k + 4), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 0, k + 5), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 0, k + 6), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 1, k + 0), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 1, k + 1), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 1, k + 2), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 1, k + 3), Blocks.glowstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 1, k + 4), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 1, k + 5), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 1, k + 6), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 2, k + 0), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 2, k + 1), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 2, k + 2), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 2, k + 3), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 2, k + 4), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 2, k + 5), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 2, k + 6), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 0, k + 0), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 0, k + 1), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 0, k + 2), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 0, k + 3), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 0, k + 4), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 0, k + 5), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 0, k + 6), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 1, k + 0), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 1, k + 1), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 1, k + 2), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 1, k + 3), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 1, k + 4), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 1, k + 5), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 1, k + 6), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 2, k + 0), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 2, k + 1), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 2, k + 2), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 2, k + 3), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 2, k + 4), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 2, k + 5), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 2, k + 6), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 0, k + 0), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 0, k + 1), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 0, k + 2), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 0, k + 3), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 0, k + 4), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 0, k + 5), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 0, k + 6), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 1, k + 0), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 1, k + 2), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 1, k + 3), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 1, k + 4), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 1, k + 6), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 2, k + 0), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 2, k + 1), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 2, k + 2), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 2, k + 3), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 2, k + 4), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 2, k + 5), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 2, k + 6), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 0, k + 0), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 0, k + 1), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 0, k + 2), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 0, k + 3), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 0, k + 4), ACBlocks.darkstone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 0, k + 5), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 0, k + 6), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 1, k + 0), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 1, k + 1), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 1, k + 2), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 1, k + 3), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 1, k + 4), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 1, k + 5), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 1, k + 6), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 2, k + 0), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 2, k + 1), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 2, k + 2), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 2, k + 3), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 2, k + 4), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 2, k + 5), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 2, k + 6), Blocks.air.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 1, j + 1, k + 1), Blocks.redstone_torch.getStateFromMeta(5), 2); //TODO: 5
		world.setBlockState(new BlockPos(i + 1, j + 1, k + 5), Blocks.redstone_torch.getStateFromMeta(5), 2); //TODO: 5
		world.setBlockState(new BlockPos(i + 5, j + 1, k + 1), Blocks.redstone_torch.getStateFromMeta(5), 2); //TODO: 5
		world.setBlockState(new BlockPos(i + 5, j + 1, k + 5), Blocks.redstone_torch.getStateFromMeta(5), 2); //TODO: 5

		return true;
	}
}