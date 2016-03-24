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
package com.shinoow.abyssalcraft.common.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class GenLayerBiomesDL extends GenLayer {

	protected BiomeGenBase[] allowedBiomes = {AbyssalCraft.Dreadlands, AbyssalCraft.AbyDreadlands, AbyssalCraft.ForestDreadlands, AbyssalCraft.MountainDreadlands};

	public GenLayerBiomesDL(long seed, GenLayer genlayer) {
		super(seed);
		parent = genlayer;
	}

	public GenLayerBiomesDL(long seed) {
		super(seed);
	}

	@Override
	public int[] getInts(int x, int z, int width, int depth)
	{
		int[] dest = IntCache.getIntCache(width*depth);

		for (int dz=0; dz<depth; dz++)
			for (int dx=0; dx<width; dx++)
			{
				initChunkSeed(dx+x, dz+z);
				dest[dx+dz*width] = BiomeGenBase.getIdForBiome(allowedBiomes[nextInt(allowedBiomes.length)]);
			}
		return dest;
	}
}
