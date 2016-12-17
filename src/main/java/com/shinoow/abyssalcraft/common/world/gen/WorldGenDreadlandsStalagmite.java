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
package com.shinoow.abyssalcraft.common.world.gen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.block.ACBlocks;

public class WorldGenDreadlandsStalagmite extends WorldGenerator {

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {

		while(world.isAirBlock(pos))
			pos = pos.down();

		if(world.getBlockState(pos) != ACBlocks.abyssalnite_stone.getDefaultState() &&
				world.getBlockState(pos) != ACBlocks.dreadstone.getDefaultState())
			return false;

		IBlockState state;
		if(world.getBiome(pos) == ACBiomes.purified_dreadlands)
			state = ACBlocks.abyssalnite_stone.getDefaultState();
		else state = ACBlocks.dreadstone.getDefaultState();

		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		for(int i = 0; i < 7 + rand.nextInt(5); i++)
			world.setBlockState(new BlockPos(x, y + i, z), state, 2);
		for(int i = 0; i < 5 + rand.nextInt(5); i++)
			world.setBlockState(new BlockPos(x + 1, y + i, z), state, 2);
		for(int i = 0; i < 5 + rand.nextInt(5); i++)
			world.setBlockState(new BlockPos(x - 1, y + i, z), state, 2);
		for(int i = 0; i < 5 + rand.nextInt(5); i++)
			world.setBlockState(new BlockPos(x, y + i, z + 1), state, 2);
		for(int i = 0; i < 5 + rand.nextInt(5); i++)
			world.setBlockState(new BlockPos(x, y + i, z - 1), state, 2);
		for(int i = 0; i < 3 + rand.nextInt(5); i++)
			world.setBlockState(new BlockPos(x + 1, y + i, z + 1), state, 2);
		for(int i = 0; i < 3 + rand.nextInt(5); i++)
			world.setBlockState(new BlockPos(x - 1, y + i, z - 1), state, 2);
		for(int i = 0; i < 3 + rand.nextInt(5); i++)
			world.setBlockState(new BlockPos(x - 1, y + i, z + 1), state, 2);
		for(int i = 0; i < 3 + rand.nextInt(5); i++)
			world.setBlockState(new BlockPos(x + 1, y + i, z - 1), state, 2);

		return true;
	}
}