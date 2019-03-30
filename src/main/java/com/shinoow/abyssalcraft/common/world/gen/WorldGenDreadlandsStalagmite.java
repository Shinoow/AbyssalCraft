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

		Chunk chunk1 = getChunk(world, pos.east(), chunk);
		Chunk chunk2 = getChunk(world, pos.west(), chunk);
		Chunk chunk3 = getChunk(world, pos.south(), chunk);
		Chunk chunk4 = getChunk(world, pos.north(), chunk);
		Chunk chunk5 = getChunk(world, pos.south().east(), chunk);
		Chunk chunk6 = getChunk(world, pos.north().west(), chunk);
		Chunk chunk7 = getChunk(world, pos.south().west(), chunk);
		Chunk chunk8 = getChunk(world, pos.north().east(), chunk);

		for(int i = 0; i < 7 + rand.nextInt(5); i++)
			chunk.setBlockState(pos.up(i), state);
		for(int i = 0; i < 5 + rand.nextInt(5); i++)
			chunk1.setBlockState(pos.east().up(i), state);
		for(int i = 0; i < 5 + rand.nextInt(5); i++)
			chunk2.setBlockState(pos.west().up(i), state);
		for(int i = 0; i < 5 + rand.nextInt(5); i++)
			chunk3.setBlockState(pos.south().up(i), state);
		for(int i = 0; i < 5 + rand.nextInt(5); i++)
			chunk4.setBlockState(pos.north().up(i), state);
		for(int i = 0; i < 3 + rand.nextInt(5); i++)
			chunk5.setBlockState(pos.south().east().up(i), state);
		for(int i = 0; i < 3 + rand.nextInt(5); i++)
			chunk6.setBlockState(pos.north().west().up(i), state);
		for(int i = 0; i < 3 + rand.nextInt(5); i++)
			chunk7.setBlockState(pos.south().west().up(i), state);
		for(int i = 0; i < 3 + rand.nextInt(5); i++)
			chunk8.setBlockState(pos.north().east().up(i), state);

		return true;
	}

	private Chunk getChunk(World world, BlockPos pos, Chunk chunk) {
		if(pos.getX() >> 4 == chunk.x && pos.getZ() >> 4 == chunk.z)
			return chunk;
		return world.getChunkFromBlockCoords(pos);
	}
}
