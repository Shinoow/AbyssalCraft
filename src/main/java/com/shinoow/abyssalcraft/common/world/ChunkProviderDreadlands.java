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

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.CAVE;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.NETHER_CAVE;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.RAVINE;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.NETHER_LAVA;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.MapGenCavesHell;
import net.minecraft.world.gen.MapGenRavine;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenHellLava;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.TerrainGen;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.structures.StructureShoggothPit;
import com.shinoow.abyssalcraft.common.structures.dreadlands.mineshaft.MapGenDreadlandsMine;

public class ChunkProviderDreadlands implements IChunkGenerator {

	private Random rand;

	private NoiseGeneratorOctaves noiseGen1;
	private NoiseGeneratorOctaves noiseGen2;
	private NoiseGeneratorOctaves noiseGen3;
	private NoiseGeneratorPerlin noiseGen4;
	public NoiseGeneratorOctaves noiseGen5;
	public NoiseGeneratorOctaves noiseGen6;

	/** Reference to the World object. */
	private World worldObj;
	private final boolean mapFeaturesEnabled;
	private WorldType worldType;
	private final double[] field_147434_q;
	private final float[] parabolicField;
	private double[] stoneNoise = new double[256];
	private MapGenBase caveGenerator = new MapGenCaves();
	private MapGenBase netherCaveGenerator = new MapGenCavesHell();

	private MapGenDreadlandsMine dmGenerator = new MapGenDreadlandsMine();

	/** Holds ravine generator */
	private MapGenBase ravineGenerator = new MapGenRavine();

	/** The biomes that are used to generate the chunk */
	private Biome[] biomesForGeneration;

	double[] doubleArray1;
	double[] doubleArray2;
	double[] doubleArray3;
	double[] doubleArray4;
	int[][] field_73219_j = new int[32][32];

	{
		caveGenerator = TerrainGen.getModdedMapGen(caveGenerator, CAVE);
		ravineGenerator = TerrainGen.getModdedMapGen(ravineGenerator, RAVINE);
		netherCaveGenerator = TerrainGen.getModdedMapGen(netherCaveGenerator, NETHER_CAVE);
	}

	public ChunkProviderDreadlands(World par1World, long par2, boolean par4)
	{
		worldObj = par1World;
		mapFeaturesEnabled = par4;
		worldType = par1World.getWorldInfo().getTerrainType();
		rand = new Random(par2);
		noiseGen1 = new NoiseGeneratorOctaves(rand, 16);
		noiseGen2 = new NoiseGeneratorOctaves(rand, 16);
		noiseGen3 = new NoiseGeneratorOctaves(rand, 8);
		noiseGen4 = new NoiseGeneratorPerlin(rand, 4);
		noiseGen5 = new NoiseGeneratorOctaves(rand, 10);
		noiseGen6 = new NoiseGeneratorOctaves(rand, 16);
		field_147434_q = new double[825];
		parabolicField = new float[25];

		for (int j = -2; j <= 2; ++j)
			for (int k = -2; k <= 2; ++k)
			{
				float f = 10.0F / MathHelper.sqrt_float(j * j + k * k + 0.2F);
				parabolicField[j + 2 + (k + 2) * 5] = f;
			}
	}

	public void setBlocksInChunk(int par1, int par2, ChunkPrimer primer)
	{
		byte b0 = 63;
		biomesForGeneration = worldObj.getBiomeProvider().getBiomesForGeneration(biomesForGeneration, par1 * 4 - 2, par2 * 4 - 2, 10, 10);
		generateNoise(par1 * 4, 0, par2 * 4);

		for (int k = 0; k < 4; ++k)
		{
			int l = k * 5;
			int i1 = (k + 1) * 5;

			for (int j1 = 0; j1 < 4; ++j1)
			{
				int k1 = (l + j1) * 33;
				int l1 = (l + j1 + 1) * 33;
				int i2 = (i1 + j1) * 33;
				int j2 = (i1 + j1 + 1) * 33;

				for (int k2 = 0; k2 < 32; ++k2)
				{
					double d0 = 0.125D;
					double d1 = field_147434_q[k1 + k2];
					double d2 = field_147434_q[l1 + k2];
					double d3 = field_147434_q[i2 + k2];
					double d4 = field_147434_q[j2 + k2];
					double d5 = (field_147434_q[k1 + k2 + 1] - d1) * d0;
					double d6 = (field_147434_q[l1 + k2 + 1] - d2) * d0;
					double d7 = (field_147434_q[i2 + k2 + 1] - d3) * d0;
					double d8 = (field_147434_q[j2 + k2 + 1] - d4) * d0;

					for (int l2 = 0; l2 < 8; ++l2)
					{
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;

						for (int i3 = 0; i3 < 4; ++i3)
						{
							double d14 = 0.25D;
							double d16 = (d11 - d10) * d14;
							double d15 = d10 - d16;

							for (int k3 = 0; k3 < 4; ++k3)
								if ((d15 += d16) > 0.0D)
									primer.setBlockState(k * 4 + i3, k2 * 8 + l2, j1 * 4 + k3, ACBlocks.dreadstone.getDefaultState());
								else if (k2 * 8 + l2 < b0)
									primer.setBlockState(k * 4 + i3, k2 * 8 + l2, j1 * 4 + k3, ACBlocks.abyssalnite_stone.getDefaultState());

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
		}
	}

	public void replaceBlocksForBiome(int par1, int par2, ChunkPrimer primer, Biome[] par5BiomeArray)
	{
		if(!ForgeEventFactory.onReplaceBiomeBlocks(this, par1, par2, primer, worldObj)) return;

		double d0 = 0.03125D;
		stoneNoise = noiseGen4.getRegion(stoneNoise, par1 * 16, par2 * 16, 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

		for (int k = 0; k < 16; ++k)
			for (int l = 0; l < 16; ++l)
			{
				Biome Biome = par5BiomeArray[l + k * 16];
				Biome.genTerrainBlocks(worldObj, rand, primer, par1 * 16 + k, par2 * 16 + l, stoneNoise[l + k * 16]);
			}
	}

	/**
	 * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
	 * specified chunk from the map seed and chunk seed
	 */
	@Override
	public Chunk provideChunk(int par1, int par2)
	{
		rand.setSeed(par1 * 341873128712L + par2 * 132897987541L);
		ChunkPrimer primer = new ChunkPrimer();
		setBlocksInChunk(par1, par2, primer);
		biomesForGeneration = worldObj.getBiomeProvider().loadBlockGeneratorData(biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
		replaceBlocksForBiome(par1, par2, primer, biomesForGeneration);
		caveGenerator.generate(worldObj, par1, par2, primer);
		ravineGenerator.generate(worldObj, par1, par2, primer);
		netherCaveGenerator.generate(worldObj, par1, par2, primer);

		if (mapFeaturesEnabled)
			dmGenerator.generate(worldObj, par1, par2, primer);

		Chunk chunk = new Chunk(worldObj, primer, par1, par2);
		byte[] abyte1 = chunk.getBiomeArray();

		for (int k = 0; k < abyte1.length; ++k)
			abyte1[k] = (byte)Biome.getIdForBiome(biomesForGeneration[k]);

		chunk.generateSkylightMap();
		return chunk;
	}

	private void generateNoise(int par1, int par2, int par3)
	{
		doubleArray4 = noiseGen6.generateNoiseOctaves(doubleArray4, par1, par3, 5, 5, 200.0D, 200.0D, 0.5D);
		doubleArray1 = noiseGen3.generateNoiseOctaves(doubleArray1, par1, par2, par3, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
		doubleArray2 = noiseGen1.generateNoiseOctaves(doubleArray2, par1, par2, par3, 5, 33, 5, 684.412D, 684.412D, 684.412D);
		doubleArray3 = noiseGen2.generateNoiseOctaves(doubleArray3, par1, par2, par3, 5, 33, 5, 684.412D, 684.412D, 684.412D);
		int l = 0;
		int i1 = 0;
		for (int j1 = 0; j1 < 5; ++j1)
			for (int k1 = 0; k1 < 5; ++k1)
			{
				float f = 0.0F;
				float f1 = 0.0F;
				float f2 = 0.0F;
				byte b0 = 2;
				Biome Biome = biomesForGeneration[j1 + 2 + (k1 + 2) * 10];

				for (int l1 = -b0; l1 <= b0; ++l1)
					for (int i2 = -b0; i2 <= b0; ++i2)
					{
						Biome Biome1 = biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
						float f3 = Biome1.getBaseHeight();
						float f4 = Biome1.getHeightVariation();

						if (worldType == WorldType.AMPLIFIED && f3 > 0.0F)
						{
							f3 = 1.0F + f3 * 2.0F;
							f4 = 1.0F + f4 * 4.0F;
						}

						float f5 = parabolicField[l1 + 2 + (i2 + 2) * 5] / (f3 + 2.0F);

						if (Biome1.getBaseHeight() > Biome.getBaseHeight())
							f5 /= 2.0F;

						f += f4 * f5;
						f1 += f3 * f5;
						f2 += f5;
					}

				f /= f2;
				f1 /= f2;
				f = f * 0.9F + 0.1F;
				f1 = (f1 * 4.0F - 1.0F) / 8.0F;
				double d13 = doubleArray4[i1] / 8000.0D;

				if (d13 < 0.0D)
					d13 = -d13 * 0.3D;

				d13 = d13 * 3.0D - 2.0D;

				if (d13 < 0.0D)
				{
					d13 /= 2.0D;

					if (d13 < -1.0D)
						d13 = -1.0D;

					d13 /= 1.4D;
					d13 /= 2.0D;
				}
				else
				{
					if (d13 > 1.0D)
						d13 = 1.0D;

					d13 /= 8.0D;
				}

				++i1;
				double d12 = f1;
				double d14 = f;
				d12 += d13 * 0.2D;
				d12 = d12 * 8.5D / 8.0D;
				double d5 = 8.5D + d12 * 4.0D;

				for (int j2 = 0; j2 < 33; ++j2)
				{
					double d6 = (j2 - d5) * 12.0D * 128.0D / 256.0D / d14;

					if (d6 < 0.0D)
						d6 *= 4.0D;

					double d7 = doubleArray2[l] / 512.0D;
					double d8 = doubleArray3[l] / 512.0D;
					double d9 = (doubleArray1[l] / 10.0D + 1.0D) / 2.0D;
					double d10 = MathHelper.denormalizeClamp(d7, d8, d9) - d6;

					if (j2 > 29)
					{
						double d11 = (j2 - 29) / 3.0F;
						d10 = d10 * (1.0D - d11) + -10.0D * d11;
					}

					field_147434_q[l] = d10;
					++l;
				}
			}
	}

	/**
	 * Populates chunk with ores etc etc
	 */
	@Override
	public void populate(int par2, int par3)
	{
		BlockFalling.fallInstantly = true;
		int k = par2 * 16;
		int l = par3 * 16;
		Biome Biome = worldObj.getBiomeGenForCoords(new BlockPos(k + 16, 0, l + 16));
		rand.setSeed(worldObj.getSeed());
		long i1 = rand.nextLong() / 2L * 2L + 1L;
		long j1 = rand.nextLong() / 2L * 2L + 1L;
		rand.setSeed(par2 * i1 + par3 * j1 ^ worldObj.getSeed());
		boolean flag = false;

		ForgeEventFactory.onChunkPopulate(true, this, worldObj, rand, par2, par3, flag);

		if (mapFeaturesEnabled)
			dmGenerator.generateStructure(worldObj, rand, new ChunkPos(par2, par3));

		int k1;
		int l1;
		int i2;

		boolean doGen = TerrainGen.populate(this, worldObj, rand, par2, par3, false, NETHER_LAVA);
		for (i1 = 0; doGen && i1 < 8; ++i1)
		{
			k1 = k + rand.nextInt(16) + 8;
			l1 = rand.nextInt(120) + 4;
			i2 = l + rand.nextInt(16) + 8;
			new WorldGenHellLava(Blocks.LAVA, false).generate(worldObj, rand, new BlockPos(k1, l1, i2));
		}

		if(AbyssalCraft.generateShoggothLairs)
			for(int i = 0; i < 1; i++) {
				int Xcoord2 = k + rand.nextInt(16);
				int Zcoord2 = l + rand.nextInt(16);

				if(rand.nextInt(400) == 0)
					new StructureShoggothPit().generate(worldObj, rand, worldObj.getHeight(new BlockPos(Xcoord2, 0, Zcoord2)));
			}

		Biome.decorate(worldObj, rand, new BlockPos(k, 0, l));


		ForgeEventFactory.onChunkPopulate(false, this, worldObj, rand, par2, par3, flag);

		BlockFalling.fallInstantly = false;
	}

	/**
	 * Returns a list of creatures of the specified type that can spawn at the given location.
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, BlockPos pos)
	{
		Biome biome = worldObj.getBiomeGenForCoords(pos);
		return biome == null ? null : biome.getSpawnableList(par1EnumCreatureType);
	}

	@Override
	public BlockPos getStrongholdGen(World p_147416_1_, String p_147416_2_, BlockPos pos)
	{
		return null;
	}

	@Override
	public void recreateStructures(Chunk chunk, int par1, int par2)
	{
		if (mapFeaturesEnabled)
			dmGenerator.generate(worldObj, par1, par2, (ChunkPrimer)null);
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) {

		return false;
	}
}