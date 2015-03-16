/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
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

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.common.eventhandler.Event.Result;

public class ChunkProviderOmothol implements IChunkProvider
{
	private Random rand;
	private NoiseGeneratorOctaves noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5;
	private World worldObj;
	private double[] densities;
	/** The biomes that are used to generate the chunk */
	private BiomeGenBase[] biomesForGeneration;
	double[] noiseData1, noiseData2, noiseData3, noiseData4, noiseData5;
	int[][] field_73203_h = new int[32][32];

	public ChunkProviderOmothol(World par1World, long par2)
	{
		worldObj = par1World;
		rand = new Random(par2);
		noiseGen1 = new NoiseGeneratorOctaves(rand, 16);
		noiseGen2 = new NoiseGeneratorOctaves(rand, 16);
		noiseGen3 = new NoiseGeneratorOctaves(rand, 8);
		noiseGen4 = new NoiseGeneratorOctaves(rand, 10);
		noiseGen5 = new NoiseGeneratorOctaves(rand, 16);

		NoiseGenerator[] noiseGens = {noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5};
		noiseGens = TerrainGen.getModdedNoiseGenerators(par1World, rand, noiseGens);
		noiseGen1 = (NoiseGeneratorOctaves)noiseGens[0];
		noiseGen2 = (NoiseGeneratorOctaves)noiseGens[1];
		noiseGen3 = (NoiseGeneratorOctaves)noiseGens[2];
		noiseGen4 = (NoiseGeneratorOctaves)noiseGens[3];
		noiseGen5 = (NoiseGeneratorOctaves)noiseGens[4];
	}

	public void generateTerrain(int x, int z, Block[] par3BlockArray, BiomeGenBase[] par4BiomeArray)
	{
		byte b0 = 2;
		int k = b0 + 1;
		byte b1 = 33;
		int l = b0 + 1;
		densities = initializeNoiseField(densities, x * b0, 0, z * b0, k, b1, l);

		for (int i1 = 0; i1 < b0; ++i1)
			for (int j1 = 0; j1 < b0; ++j1)
				for (int k1 = 0; k1 < 32; ++k1)
				{
					double d0 = 0.25D;
					double d1 = densities[((i1 + 0) * l + j1 + 0) * b1 + k1 + 0];
					double d2 = densities[((i1 + 0) * l + j1 + 1) * b1 + k1 + 0];
					double d3 = densities[((i1 + 1) * l + j1 + 0) * b1 + k1 + 0];
					double d4 = densities[((i1 + 1) * l + j1 + 1) * b1 + k1 + 0];
					double d5 = (densities[((i1 + 0) * l + j1 + 0) * b1 + k1 + 1] - d1) * d0;
					double d6 = (densities[((i1 + 0) * l + j1 + 1) * b1 + k1 + 1] - d2) * d0;
					double d7 = (densities[((i1 + 1) * l + j1 + 0) * b1 + k1 + 1] - d3) * d0;
					double d8 = (densities[((i1 + 1) * l + j1 + 1) * b1 + k1 + 1] - d4) * d0;

					for (int l1 = 0; l1 < 4; ++l1)
					{
						double d9 = 0.125D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;

						for (int i2 = 0; i2 < 8; ++i2)
						{
							int j2 = i2 + i1 * 8 << 11 | 0 + j1 * 8 << 7 | k1 * 4 + l1;
							short short1 = 128;
							double d14 = 0.125D;
							double d15 = d10;
							double d16 = (d11 - d10) * d14;

							for (int k2 = 0; k2 < 8; ++k2)
							{
								Block block = null;

								if (d15 > 0.0D)
									block = AbyssalCraft.omotholstone;

								par3BlockArray[j2] = block;
								j2 += short1;
								d15 += d16;
							}

							d10 += d12;
							d11 += d13;
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
	}

	public void replaceBlocksForBiome(int x, int z, Block[] par3BlockArray, BiomeGenBase[] par4BiomeArray)
	{
		ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, x, z, par3BlockArray, par4BiomeArray);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY) return;

		for (int k = 0; k < 16; ++k)
			for (int l = 0; l < 16; ++l)
			{
				byte b0 = 1;
				int i1 = -1;
				Block block = AbyssalCraft.omotholstone;
				Block block1 = AbyssalCraft.omotholstone;

				for (int j1 = 127; j1 >= 0; --j1)
				{
					int k1 = (l * 16 + k) * 128 + j1;
					Block block2 = par3BlockArray[k1];

					if (block2 != null && block2.getMaterial() != Material.air)
					{
						if (block2 == AbyssalCraft.omotholstone)
							if (i1 == -1)
							{
								if (b0 <= 0)
								{
									block = null;
									block1 = AbyssalCraft.omotholstone;
								}

								i1 = b0;

								if (j1 >= 0)
									par3BlockArray[k1] = block;
								else
									par3BlockArray[k1] = block1;
							}
							else if (i1 > 0)
							{
								--i1;
								par3BlockArray[k1] = block1;
							}
					} else
						i1 = -1;
				}
			}
	}

	@Override
	public Chunk loadChunk(int x, int z)
	{
		return provideChunk(x, z);
	}

	@Override
	public Chunk provideChunk(int x, int z)
	{
		rand.setSeed(x * 341873128712L + z * 132897987541L);
		Block[] ablock = new Block[32768];
		biomesForGeneration = worldObj.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, x * 16, z * 16, 16, 16);
		generateTerrain(x, z, ablock, biomesForGeneration);
		replaceBlocksForBiome(x, z, ablock, biomesForGeneration);
		Chunk chunk = new Chunk(worldObj, ablock, x, z);
		byte[] abyte = chunk.getBiomeArray();

		for (int k = 0; k < abyte.length; ++k)
			abyte[k] = (byte)biomesForGeneration[k].biomeID;

		chunk.generateSkylightMap();
		return chunk;
	}

	/**
	 * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
	 * size.
	 */
	private double[] initializeNoiseField(double[] par1ArrayOfDouble, int x, int y, int z, int xSize, int ySize, int zSize)
	{
		if(par1ArrayOfDouble == null)
			par1ArrayOfDouble = new double[xSize * ySize * zSize];
		double d = 684.41200000000003D;
		double d1 = 684.41200000000003D;
		noiseData4 = noiseGen4.generateNoiseOctaves(noiseData4, x, z, xSize, zSize, 1.121D, 1.121D, 0.5D);
		noiseData5 = noiseGen5.generateNoiseOctaves(noiseData5, x, z, xSize, zSize, 200D, 200D, 0.5D);
		d *= 2D;
		noiseData1 = noiseGen3.generateNoiseOctaves(noiseData1, x, y, z, xSize, ySize, zSize, d / 80D, d1 / 160D, d / 80D);
		noiseData2 = noiseGen1.generateNoiseOctaves(noiseData2, x, y, z, xSize, ySize, zSize, d, d1, d);
		noiseData3 = noiseGen2.generateNoiseOctaves(noiseData3, x, y, z, xSize, ySize, zSize, d, d1, d);
		int k1 = 0;
		int l1 = 0;
		for(int j2 = 0; j2 < xSize; j2++)
			for(int l2 = 0; l2 < zSize; l2++)
			{
				double d3;
				d3 = 0.5D;
				double d4 = 1.0D - d3;
				d4 *= d4;
				d4 *= d4;
				d4 = 1.0D - d4;
				double d5 = (noiseData4[l1] + 256D) / 512D;
				d5 *= d4;
				if(d5 > 1.0D)
					d5 = 1.0D;
				double d6 = noiseData5[l1] / 8000D;
				if(d6 < 0.0D)
					d6 = -d6 * 0.29999999999999999D;
				d6 = d6 * 3D - 2D;
				if(d6 > 1.0D)
					d6 = 1.0D;
				d6 /= 8D;
				d6 = 0.0D;
				if(d5 < 0.0D)
					d5 = 0.0D;
				d5 += 0.5D;
				d6 = d6 * ySize / 16D;
				l1++;
				double d7 = ySize / 2D;
				for(int j3 = 0; j3 < ySize; j3++)
				{
					double d8 = 0.0D;
					double d9 = (j3 - d7) * 8D / d5;
					if(d9 < 0.0D)
						d9 *= -1D;
					double d10 = noiseData2[k1] / 512D;
					double d11 = noiseData3[k1] / 512D;
					double d12 = (noiseData1[k1] / 10D + 1.0D) / 2D;
					if(d12 < 0.0D)
						d8 = d10;
					else
						if(d12 > 1.0D)
							d8 = d11;
						else
							d8 = d10 + (d11 - d10) * d12;
					d8 -= 8D;
					int k3 = 32;
					if(j3 > ySize - k3)
					{
						double d13 = (j3 - (ySize - k3)) / (k3 - 1.0F);
						d8 = d8 * (1.0D - d13) + -30D * d13;
					}
					k3 = 8;
					if(j3 < k3)
					{
						double d14 = (k3 - j3) / (k3 - 1.0F);
						d8 = d8 * (1.0D - d14) + -30D * d14;
					}
					par1ArrayOfDouble[k1] = d8;
					k1++;
				}

			}

		return par1ArrayOfDouble;
	}

	@Override
	public boolean chunkExists(int x, int z)
	{
		return true;
	}

	@Override
	public void populate(IChunkProvider par1IChunkProvider, int x, int z)
	{
		BlockFalling.fallInstantly = true;

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(par1IChunkProvider, worldObj, worldObj.rand, x, z, false));

		int k = x * 16;
		int l = z * 16;
		BiomeGenBase biomegenbase = worldObj.getBiomeGenForCoords(k + 16, l + 16);
		biomegenbase.decorate(worldObj, worldObj.rand, k, l);

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(par1IChunkProvider, worldObj, worldObj.rand, x, z, false));

		BlockFalling.fallInstantly = false;
	}

	@Override
	public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
	{
		return true;
	}

	@Override
	public void saveExtraData() {}

	@Override
	public boolean unloadQueuedChunks()
	{
		return false;
	}

	@Override
	public boolean canSave()
	{
		return true;
	}

	@Override
	public String makeString()
	{
		return "ACLevelSource";
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int x, int y, int z)
	{
		BiomeGenBase biomegenbase = worldObj.getBiomeGenForCoords(x, z);
		return biomegenbase.getSpawnableList(par1EnumCreatureType);
	}

	@Override
	public ChunkPosition func_147416_a(World par1World, String par2String, int x, int y, int z)
	{
		return null;
	}

	@Override
	public int getLoadedChunkCount()
	{
		return 0;
	}

	@Override
	public void recreateStructures(int x, int z) {}
}