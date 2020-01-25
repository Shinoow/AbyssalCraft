/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2019 Shinoow.
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

import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.block.ACBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDreadlandsStalagmite extends WorldGenerator {

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {

		Chunk chunk = world.getChunkFromBlockCoords(pos);

		while(chunk.getBlockState(pos).getBlock().isAir(chunk.getBlockState(pos), world, pos))
			pos = pos.down();

		if(chunk.getBlockState(pos) != ACBlocks.stone.getStateFromMeta(3) &&
				chunk.getBlockState(pos) != ACBlocks.stone.getStateFromMeta(2))
			return false;

		IBlockState state;
		if(world.getBiome(pos) == ACBiomes.purified_dreadlands)
			state = ACBlocks.stone.getStateFromMeta(3);
		else state = ACBlocks.stone.getStateFromMeta(2);

		placeBlocks(world, chunk, state, pos, 7 + rand.nextInt(5));
		placeBlocksAlt(world, chunk, state, pos.east(), 5 + rand.nextInt(5));
		placeBlocksAlt(world, chunk, state, pos.west(), 5 + rand.nextInt(5));
		placeBlocksAlt(world, chunk, state, pos.south(), 5 + rand.nextInt(5));
		placeBlocksAlt(world, chunk, state, pos.north(), 5 + rand.nextInt(5));
		placeBlocksAlt(world, chunk, state, pos.south().east(), 3 + rand.nextInt(5));
		placeBlocksAlt(world, chunk, state, pos.north().west(), 3 + rand.nextInt(5));
		placeBlocksAlt(world, chunk, state, pos.south().west(), 3 + rand.nextInt(5));
		placeBlocksAlt(world, chunk, state, pos.north().east(), 3 + rand.nextInt(5));

		return true;
	}

	private void placeBlocks(World world, Chunk chunk, IBlockState state, BlockPos pos, int height) {
		for(int i = 0; i < height; i++)
			if(!world.isOutsideBuildHeight(pos.up(i)))
				chunk.setBlockState(pos.up(i), state);
	}

	private void placeBlocksAlt(World world, Chunk chunk, IBlockState state, BlockPos pos, int height) {
		Chunk chunk1 = getChunk(world, pos, chunk);
		placeBlocks(world, chunk, state, pos, height);
	}

	private Chunk getChunk(World world, BlockPos pos, Chunk chunk) {
		if(pos.getX() >> 4 == chunk.x && pos.getZ() >> 4 == chunk.z)
			return chunk;
		return world.getChunkFromBlockCoords(pos);
	}
}
