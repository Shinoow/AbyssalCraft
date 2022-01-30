/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.util;

import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.CleansingRitualMessage;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

/**
 * Utility class that handles biome transformations (converting Darklands back and forth, turning biomes into Dreadlands, purging Dreadlands)
 * @author sk2048
 *
 */
public class BiomeUtil {

	/**
	 * Changes biome at the specified position with the specified one
	 * @param worldIn Current World
	 * @param pos Current Position
	 * @param b Replacement biome
	 */
	public static void updateBiome(World worldIn, BlockPos pos, Biome b) {
		updateBiome(worldIn, pos, b, false);
	}

	/**
	 * Changes biome at the specified position with the specified one
	 * @param worldIn Current World
	 * @param pos Current Position
	 * @param b Replacement biome
	 * @param batched Whether or not to cause a render update in the nearby area (false will force it, potentially affecting performance
	 * depending on how many chunks are being altered at once)
	 */
	public static void updateBiome(World worldIn, BlockPos pos, Biome b, boolean batched) {
		updateBiome(worldIn, pos, Biome.getIdForBiome(b), batched);
	}

	/**
	 * Changes biome at the specified position with the specified one
	 * @param worldIn Current World
	 * @param pos Current Position
	 * @param b Replacement biome ID
	 * @param batched Whether or not to cause a render update in the nearby area (false will force it, potentially affecting performance
	 * depending on how many chunks are being altered at once)
	 */
	public static void updateBiome(World worldIn, BlockPos pos, int b, boolean batched) {
		Chunk c = worldIn.getChunk(pos);
		c.getBiomeArray()[(pos.getZ() & 0xF) << 4 | pos.getX() & 0xF] = (byte)b;
		c.setModified(true);
		if(!worldIn.isRemote)
			PacketDispatcher.sendToDimension(new CleansingRitualMessage(pos.getX(), pos.getZ(), b, batched), worldIn.provider.getDimension());
	}
}
