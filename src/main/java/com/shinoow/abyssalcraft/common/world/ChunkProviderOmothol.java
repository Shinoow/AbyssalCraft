package com.shinoow.abyssalcraft.common.world;

import java.util.List;
import java.util.Random;

import com.shinoow.abyssalcraft.AbyssalCraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.common.*;
import cpw.mods.fml.common.eventhandler.Event.*;
import net.minecraftforge.event.terraingen.*;

public class ChunkProviderOmothol implements IChunkProvider
{
	private Random rand;
	private NoiseGeneratorOctaves noiseGen1;
	private NoiseGeneratorOctaves noiseGen2;
	private NoiseGeneratorOctaves noiseGen3;
	public NoiseGeneratorOctaves noiseGen4;
	public NoiseGeneratorOctaves noiseGen5;
	private World worldObj;
	private double[] densities;
	/** The biomes that are used to generate the chunk */
	private BiomeGenBase[] biomesForGeneration;
	double[] noiseData1;
	double[] noiseData2;
	double[] noiseData3;
	double[] noiseData4;
	double[] noiseData5;
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

	public void generateTerrain(int par1, int par2, Block[] par3BlockArray, BiomeGenBase[] par4BiomeArray)
	{
		byte b0 = 2;
		int k = b0 + 1;
		byte b1 = 33;
		int l = b0 + 1;
		densities = initializeNoiseField(densities, par1 * b0, 0, par2 * b0, k, b1, l);

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

	public void replaceBlocksForBiome(int par1, int par2, Block[] par3BlockArray, BiomeGenBase[] par4BiomeArray)
	{
		ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, par1, par2, par3BlockArray, par4BiomeArray);
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
						if (block2 == Blocks.stone)
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
	public Chunk loadChunk(int par1, int par2)
	{
		return provideChunk(par1, par2);
	}

	@Override
	public Chunk provideChunk(int par1, int par2)
	{
		rand.setSeed(par1 * 341873128712L + par2 * 132897987541L);
		Block[] ablock = new Block[32768];
		biomesForGeneration = worldObj.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
		generateTerrain(par1, par2, ablock, biomesForGeneration);
		replaceBlocksForBiome(par1, par2, ablock, biomesForGeneration);
		Chunk chunk = new Chunk(worldObj, ablock, par1, par2);
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
	private double[] initializeNoiseField(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7)
	{
		ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, par1ArrayOfDouble, par2, par3, par4, par5, par6, par7);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY) return event.noisefield;

		if (par1ArrayOfDouble == null)
			par1ArrayOfDouble = new double[par5 * par6 * par7];

		double d0 = 684.412D;
		double d1 = 684.412D;
		noiseData4 = noiseGen4.generateNoiseOctaves(noiseData4, par2, par4, par5, par7, 1.121D, 1.121D, 0.5D);
		noiseData5 = noiseGen5.generateNoiseOctaves(noiseData5, par2, par4, par5, par7, 200.0D, 200.0D, 0.5D);
		d0 *= 2.0D;
		noiseData1 = noiseGen3.generateNoiseOctaves(noiseData1, par2, par3, par4, par5, par6, par7, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
		noiseData2 = noiseGen1.generateNoiseOctaves(noiseData2, par2, par3, par4, par5, par6, par7, d0, d1, d0);
		noiseData3 = noiseGen2.generateNoiseOctaves(noiseData3, par2, par3, par4, par5, par6, par7, d0, d1, d0);
		int k1 = 0;
		int l1 = 0;

		for (int i2 = 0; i2 < par5; ++i2)
			for (int j2 = 0; j2 < par7; ++j2)
			{
				double d2 = (noiseData4[l1] + 256.0D) / 512.0D;

				if (d2 > 1.0D)
					d2 = 1.0D;

				double d3 = noiseData5[l1] / 8000.0D;

				if (d3 < 0.0D)
					d3 = -d3 * 0.3D;

				d3 = d3 * 3.0D - 2.0D;
				float f = (i2 + par2 - 0) / 1.0F;
				float f1 = (j2 + par4 - 0) / 1.0F;
				float f2 = 100.0F - MathHelper.sqrt_float(f * f + f1 * f1) * 8.0F;

				if (f2 > 80.0F)
					f2 = 80.0F;

				if (f2 < -100.0F)
					f2 = -100.0F;

				if (d3 > 1.0D)
					d3 = 1.0D;

				d3 /= 8.0D;
				d3 = 0.0D;

				if (d2 < 0.0D)
					d2 = 0.0D;

				d2 += 0.5D;
				d3 = d3 * par6 / 16.0D;
				++l1;
				double d4 = par6 / 2.0D;

				for (int k2 = 0; k2 < par6; ++k2)
				{
					double d5 = 0.0D;
					double d6 = (k2 - d4) * 8.0D / d2;

					if (d6 < 0.0D)
						d6 *= -1.0D;

					double d7 = noiseData2[k1] / 512.0D;
					double d8 = noiseData3[k1] / 512.0D;
					double d9 = (noiseData1[k1] / 10.0D + 1.0D) / 2.0D;

					if (d9 < 0.0D)
						d5 = d7;
					else if (d9 > 1.0D)
						d5 = d8;
					else
						d5 = d7 + (d8 - d7) * d9;

					d5 -= 8.0D;
					d5 += f2;
					byte b0 = 2;
					double d10;

					if (k2 > par6 / 2 - b0)
					{
						d10 = (k2 - (par6 / 2 - b0)) / 64.0F;

						if (d10 < 0.0D)
							d10 = 0.0D;

						if (d10 > 1.0D)
							d10 = 1.0D;

						d5 = d5 * (1.0D - d10) + -3000.0D * d10;
					}

					b0 = 8;

					if (k2 < b0)
					{
						d10 = (b0 - k2) / (b0 - 1.0F);
						d5 = d5 * (1.0D - d10) + -30.0D * d10;
					}

					par1ArrayOfDouble[k1] = d5;
					++k1;
				}
			}

		return par1ArrayOfDouble;
	}

	@Override
	public boolean chunkExists(int par1, int par2)
	{
		return true;
	}

	@Override
	public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
	{
		BlockFalling.fallInstantly = true;

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(par1IChunkProvider, worldObj, worldObj.rand, par2, par3, false));

		int k = par2 * 16;
		int l = par3 * 16;
		BiomeGenBase biomegenbase = worldObj.getBiomeGenForCoords(k + 16, l + 16);
		biomegenbase.decorate(worldObj, worldObj.rand, k, l);

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(par1IChunkProvider, worldObj, worldObj.rand, par2, par3, false));

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
		return "RandomLevelSource";
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
	{
		BiomeGenBase biomegenbase = worldObj.getBiomeGenForCoords(par2, par4);
		return biomegenbase.getSpawnableList(par1EnumCreatureType);
	}

	@Override
	public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_)
	{
		return null;
	}

	@Override
	public int getLoadedChunkCount()
	{
		return 0;
	}

	@Override
	public void recreateStructures(int par1, int par2) {}
}