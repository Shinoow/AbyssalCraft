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
package com.shinoow.abyssalcraft.common.world.gen.layer;

import com.shinoow.abyssalcraft.api.biome.ACBiomes;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerBiomesAW extends GenLayer {

	protected Biome[] allowedBiomes = {ACBiomes.abyssal_wastelands, ACBiomes.abyssal_swamp, ACBiomes.abyssal_desert, ACBiomes.abyssal_plateau};

	public GenLayerBiomesAW(long seed, GenLayer genlayer) {
		super(seed);
		parent = genlayer;
	}

	public GenLayerBiomesAW(long seed) {
		super(seed);
	}

	@Override
	public int[] getInts(int x, int z, int width, int depth)
	{
		//TODO might need more custom stuff here
		int[] dest = IntCache.getIntCache(width*depth);

		for (int dz=0; dz<depth; dz++)
			for (int dx=0; dx<width; dx++)
			{
				initChunkSeed(dx+x, dz+z);
				dest[dx+dz*width] = Biome.getIdForBiome(allowedBiomes[nextInt(allowedBiomes.length)]);
			}
		return dest;
	}
}
