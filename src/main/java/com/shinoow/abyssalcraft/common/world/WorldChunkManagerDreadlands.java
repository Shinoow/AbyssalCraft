/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.world;

import java.util.*;

import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.layer.*;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.world.gen.layer.GenLayerDL;

import cpw.mods.fml.relauncher.*;

public class WorldChunkManagerDreadlands extends WorldChunkManager
{

	private GenLayer biomeToUse;
	private GenLayer biomeIndexLayer;
	private BiomeCache biomeCache;
	private List<BiomeGenBase> biomesToSpawnIn;

	public WorldChunkManagerDreadlands()
	{
		biomeCache = new BiomeCache(this);
		biomesToSpawnIn = new ArrayList<BiomeGenBase>();
		biomesToSpawnIn.add(AbyssalCraft.Dreadlands);
		biomesToSpawnIn.add(AbyssalCraft.AbyDreadlands);
		biomesToSpawnIn.add(AbyssalCraft.ForestDreadlands);
		biomesToSpawnIn.add(AbyssalCraft.MountainDreadlands);
	}

	public WorldChunkManagerDreadlands(long par1, WorldType par3WorldType)
	{
		this();
		GenLayer[] agenlayer = GenLayerDL.makeTheWorld(par1);
		biomeToUse = agenlayer[0];
		biomeIndexLayer = agenlayer[1];
	}

	public WorldChunkManagerDreadlands(World par1world)
	{
		this(par1world.getSeed(), par1world.provider.terrainType);
	}

	@Override
	public List<BiomeGenBase> getBiomesToSpawnIn()
	{
		return biomesToSpawnIn;
	}

	@Override
	public BiomeGenBase getBiomeGenAt(int par1, int par2)
	{
		BiomeGenBase biome = biomeCache.getBiomeGenAt(par1, par2);
		if (biome == null)
			return AbyssalCraft.Dreadlands;

		return biome;
	}

	@Override
	public float[] getRainfall(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5) {
		IntCache.resetIntCache();

		if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
			par1ArrayOfFloat = new float[par4 * par5];

		int[] aint = biomeIndexLayer.getInts(par2, par3, par4, par5);

		for (int i1 = 0; i1 < par4 * par5; ++i1) {
			float f = BiomeGenBase.getBiome(aint[i1]).getIntRainfall() / 65536.0F;

			if (f > 1.0F)
				f = 1.0F;

			par1ArrayOfFloat[i1] = f;
		}

		return par1ArrayOfFloat;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getTemperatureAtHeight(float par1, int par2) {
		return par1;
	}

	public float[] getTemperatures(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5) {
		IntCache.resetIntCache();

		if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
			par1ArrayOfFloat = new float[par4 * par5];

		int[] aint = biomeIndexLayer.getInts(par2, par3, par4, par5);

		for (int i1 = 0; i1 < par4 * par5; ++i1) {
			float f = BiomeGenBase.getBiome(aint[i1]).getIntRainfall() / 65536.0F; //getIntTemperature()

			if (f > 1.0F)
				f = 1.0F;

			par1ArrayOfFloat[i1] = f;
		}

		return par1ArrayOfFloat;
	}

	@Override
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
	{
		if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5)
			par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];

		int[] aint = biomeToUse.getInts(par2, par3, par4, par5);

		for (int i = 0; i < par4 * par5; ++i)
			if (aint[i] >= 0)
				par1ArrayOfBiomeGenBase[i] = BiomeGenBase.getBiome(aint[i]);
			else
				par1ArrayOfBiomeGenBase[i] = AbyssalCraft.Dreadlands;

		return par1ArrayOfBiomeGenBase;
	}

	@Override
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
	{
		return this.getBiomeGenAt(par1ArrayOfBiomeGenBase, par2, par3, par4, par5, true);
	}

	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] par1ArrayOfBiomeGenBase, int x, int y, int width, int length, boolean cacheFlag)
	{
		IntCache.resetIntCache();

		if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < width * length)
			par1ArrayOfBiomeGenBase = new BiomeGenBase[width * length];

		if (cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (y & 15) == 0) {
			BiomeGenBase[] abiomegenbase1 = biomeCache.getCachedBiomes(x, y);
			System.arraycopy(abiomegenbase1, 0, par1ArrayOfBiomeGenBase, 0, width * length);
			return par1ArrayOfBiomeGenBase;
		} else {
			int[] aint = biomeIndexLayer.getInts(x, y, width, length);

			for (int i = 0; i < width * length; ++i)
				if (aint[i] >= 0)
					par1ArrayOfBiomeGenBase[i] = BiomeGenBase.getBiome(aint[i]);
				else
					par1ArrayOfBiomeGenBase[i] = AbyssalCraft.Dreadlands;

			return par1ArrayOfBiomeGenBase;
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean areBiomesViable(int par1, int par2, int par3, List par4List) {
		IntCache.resetIntCache();
		int l = par1 - par3 >> 2;
		int i1 = par2 - par3 >> 2;
		int j1 = par1 + par3 >> 2;
		int k1 = par2 + par3 >> 2;
		int l1 = j1 - l + 1;
		int i2 = k1 - i1 + 1;
		int[] aint = biomeToUse.getInts(l, i1, l1, i2);

		for (int j2 = 0; j2 < l1 * i2; ++j2) {
			BiomeGenBase biomegenbase = BiomeGenBase.getBiome(aint[j2]);

			if (!par4List.contains(biomegenbase))
				return false;
		}

		return true;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ChunkPosition findBiomePosition(int par1, int par2, int par3, List par4List, Random par5Random) {
		IntCache.resetIntCache();
		int l = par1 - par3 >> 2;
		int i1 = par2 - par3 >> 2;
		int j1 = par1 + par3 >> 2;
		int k1 = par2 + par3 >> 2;
		int l1 = j1 - l + 1;
		int i2 = k1 - i1 + 1;
		int[] aint = biomeToUse.getInts(l, i1, l1, i2);
		ChunkPosition chunkposition = null;
		int j2 = 0;

		for (int k2 = 0; k2 < l1 * i2; ++k2) {
			int l2 = l + k2 % l1 << 2;
			int i3 = i1 + k2 / l1 << 2;
			BiomeGenBase biomegenbase = BiomeGenBase.getBiome(aint[k2]);

			if (par4List.contains(biomegenbase) && (chunkposition == null || par5Random.nextInt(j2 + 1) == 0)) {
				chunkposition = new ChunkPosition(l2, 0, i3);
				++j2;
			}
		}

		return chunkposition;
	}

	@Override
	public void cleanupCache()
	{
		biomeCache.cleanupCache();
	}
}