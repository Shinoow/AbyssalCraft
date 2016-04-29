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
package com.shinoow.abyssalcraft.common.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.common.world.gen.layer.GenLayerDL;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
		biomesToSpawnIn.add(ACBiomes.dreadlands);
		biomesToSpawnIn.add(ACBiomes.purified_dreadlands);
		biomesToSpawnIn.add(ACBiomes.dreadlands_forest);
		biomesToSpawnIn.add(ACBiomes.dreadlands_mountains);
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
			return ACBiomes.dreadlands;

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
				par1ArrayOfBiomeGenBase[i] = ACBiomes.dreadlands;

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
					par1ArrayOfBiomeGenBase[i] = ACBiomes.dreadlands;

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
