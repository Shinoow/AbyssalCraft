/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenRavine;

import com.shinoow.abyssalcraft.api.block.ACBlocks;

public class MapGenRavineAC extends MapGenRavine {

	private boolean isACStone(Block block){
		return block == ACBlocks.darkstone || block == ACBlocks.abyssal_stone || block == ACBlocks.dreadstone ||
				block == ACBlocks.abyssalnite_stone || block == ACBlocks.coralium_stone || block == ACBlocks.omothol_stone;
	}

	@Override
	protected void digBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop)
	{
		net.minecraft.world.biome.Biome biome = world.getBiome(new BlockPos(x + chunkX * 16, 0, z + chunkZ * 16));
		IBlockState state = data.getBlockState(x, y, z);
		IBlockState top = biome.topBlock;
		IBlockState filler = biome.fillerBlock;

		if (isACStone(state.getBlock()) || state.getBlock() == top.getBlock() || state.getBlock() == filler.getBlock())
			if (y - 1 < 10)
				data.setBlockState(x, y, z, FLOWING_LAVA);
			else
			{
				data.setBlockState(x, y, z, AIR);

				if (foundTop && data.getBlockState(x, y - 1, z).getBlock() == filler.getBlock())
					data.setBlockState(x, y - 1, z, top.getBlock().getDefaultState());
			}
	}
}
