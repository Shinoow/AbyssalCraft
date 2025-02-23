/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.world;

import java.util.List;
import java.util.Random;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.structures.StructureGraveyard;
import com.shinoow.abyssalcraft.common.structures.StructureShoggothPit;
import com.shinoow.abyssalcraft.common.structures.omothol.*;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;

public class ChunkGeneratorOmothol implements IChunkGenerator
{
	private Random rand;
	private NoiseGeneratorOctaves noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5;
	private World worldObj;
	private double[] densities;
	/** The biomes that are used to generate the chunk */
	private Biome[] biomesForGeneration;
	double[] noiseData1, noiseData2, noiseData3, noiseData4, noiseData5;
	int[][] field_73203_h = new int[32][32];
	private StructureTower towerGen = new StructureTower();
	private StructureTemple templeGen = new StructureTemple();
	private StructureCity cityGen = new StructureCity();
	private StructureStorage storageGen = new StructureStorage();
	private StructureShoggothPit shoggothLair = new StructureShoggothPit();
	private StructureGraveyard graveyard = new StructureGraveyard();

	public ChunkGeneratorOmothol(World par1World, long par2)
	{
		worldObj = par1World;
		rand = new Random(par2);
		noiseGen1 = new NoiseGeneratorOctaves(rand, 16);
		noiseGen2 = new NoiseGeneratorOctaves(rand, 16);
		noiseGen3 = new NoiseGeneratorOctaves(rand, 8);
		noiseGen4 = new NoiseGeneratorOctaves(rand, 10);
		noiseGen5 = new NoiseGeneratorOctaves(rand, 16);

		//		NoiseGenerator[] noiseGens = {noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5};
		//		noiseGens = TerrainGen.getModdedNoiseGenerators(par1World, rand, noiseGens);
		//		noiseGen1 = (NoiseGeneratorOctaves)noiseGens[0];
		//		noiseGen2 = (NoiseGeneratorOctaves)noiseGens[1];
		//		noiseGen3 = (NoiseGeneratorOctaves)noiseGens[2];
		//		noiseGen4 = (NoiseGeneratorOctaves)noiseGens[3];
		//		noiseGen5 = (NoiseGeneratorOctaves)noiseGens[4];
	}

	public void setBlocksInChunk(int x, int z, ChunkPrimer primer)
	{
		int i = 2;
		int j = i + 1;
		int k = 33;
		int l = i + 1;
		densities = initializeNoiseField(densities, x * i, 0, z * i, j, k, l);

		for (int i1 = 0; i1 < i; ++i1)
			for (int j1 = 0; j1 < i; ++j1)
				for (int k1 = 0; k1 < 32; ++k1)
				{
					double d0 = 0.25D;
					double d1 = densities[((i1 + 0) * l + j1 + 0) * k + k1 + 0];
					double d2 = densities[((i1 + 0) * l + j1 + 1) * k + k1 + 0];
					double d3 = densities[((i1 + 1) * l + j1 + 0) * k + k1 + 0];
					double d4 = densities[((i1 + 1) * l + j1 + 1) * k + k1 + 0];
					double d5 = (densities[((i1 + 0) * l + j1 + 0) * k + k1 + 1] - d1) * d0;
					double d6 = (densities[((i1 + 0) * l + j1 + 1) * k + k1 + 1] - d2) * d0;
					double d7 = (densities[((i1 + 1) * l + j1 + 0) * k + k1 + 1] - d3) * d0;
					double d8 = (densities[((i1 + 1) * l + j1 + 1) * k + k1 + 1] - d4) * d0;

					for (int l1 = 0; l1 < 4; ++l1)
					{
						double d9 = 0.125D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;

						for (int i2 = 0; i2 < 8; ++i2)
						{
							double d14 = 0.125D;
							double d15 = d10;
							double d16 = (d11 - d10) * d14;

							for (int j2 = 0; j2 < 8; ++j2)
							{
								IBlockState iblockstate = null;

								if (d15 > 0.0D)
									iblockstate = ACBlocks.omothol_stone.getDefaultState();

								int k2 = i2 + i1 * 8;
								int l2 = l1 + k1 * 4;
								int i3 = j2 + j1 * 8;
								primer.setBlockState(k2, l2, i3, iblockstate);
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

	public void replaceBlocksForBiome(ChunkPrimer primer)
	{

		for (int i = 0; i < 16; ++i)
			for (int j = 0; j < 16; ++j)
			{
				int k = 1;
				int l = -1;
				IBlockState iblockstate = ACBlocks.omothol_stone.getDefaultState();
				IBlockState iblockstate1 = ACBlocks.omothol_stone.getDefaultState();

				for (int i1 = 127; i1 >= 0; --i1)
				{
					IBlockState iblockstate2 = primer.getBlockState(i, i1, j);

					if (iblockstate2.getMaterial() == Material.AIR)
						l = -1;
					else if (iblockstate2.getBlock() == Blocks.STONE)
						if (l == -1)
						{
							if (k <= 0)
							{
								iblockstate = Blocks.AIR.getDefaultState();
								iblockstate1 = ACBlocks.omothol_stone.getDefaultState();
							}

							l = k;

							if (i1 >= 0)
								primer.setBlockState(i, i1, j, iblockstate);
							else
								primer.setBlockState(i, i1, j, iblockstate1);
						}
						else if (l > 0)
						{
							--l;
							primer.setBlockState(i, i1, j, iblockstate1);
						}
				}
			}
	}

	@Override
	public Chunk generateChunk(int x, int z)
	{
		rand.setSeed(x * 341873128712L + z * 132897987541L);
		ChunkPrimer primer = new ChunkPrimer();
		biomesForGeneration = worldObj.getBiomeProvider().getBiomes(biomesForGeneration, x * 16, z * 16, 16, 16);
		setBlocksInChunk(x, z, primer);
		replaceBlocksForBiome(primer);

		Chunk chunk = new Chunk(worldObj, primer, x, z);
		byte[] abyte = chunk.getBiomeArray();

		for (int k = 0; k < abyte.length; ++k)
			abyte[k] = (byte)Biome.getIdForBiome(biomesForGeneration[k]);

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

	//	@Override
	//	public boolean chunkExists(int x, int z)
	//	{
	//		return true;
	//	}

	@Override
	public void populate(int x, int z)
	{
		BlockFalling.fallInstantly = true;

		int k = x * 16;
		int l = z * 16;
		Biome Biome = worldObj.getBiome(new BlockPos(k + 16, 0, l + 16));

		if(x == 0 && z == 0) { //TODO revise this to something close to no cascading chunkgen
			StructureJzaharTemple temple = new StructureJzaharTemple();
			temple.generate(worldObj, rand, new BlockPos(4, worldObj.getHeight(4, 7), 7));
		}

		for(int i = 0; i < 1; i++) {
			int Xcoord2 = k + rand.nextInt(16) + 8;
			int Zcoord2 = l + rand.nextInt(2) + 28;
			BlockPos pos1 = worldObj.getHeight(new BlockPos(Xcoord2, 0, Zcoord2));
			if(worldObj.getBlockState(pos1).getMaterial() == Material.PLANTS) pos1 = pos1.down();

			if(rand.nextInt(100) == 0 && !worldObj.isAirBlock(pos1.north(13)) && !worldObj.isAirBlock(pos1.north(20)) && !worldObj.isAirBlock(pos1.north(27)))
				shoggothLair.generate(worldObj, rand, pos1);
		}

		int x2 = k + rand.nextInt(16) + 8;
		int z2 = l + rand.nextInt(16) + 8;
		BlockPos posGrave = worldObj.getHeight(new BlockPos(x2, 0, z2));

		while(worldObj.isAirBlock(posGrave) && posGrave.getY() > 2)
			posGrave = posGrave.down();
		
		if(posGrave.getY() > 10) {
			IBlockState state = worldObj.getBlockState(posGrave);
			if(rand.nextInt(50) == 0 && !state.getMaterial().isLiquid() && state.getMaterial() != Material.LEAVES
					&& state.getMaterial() != Material.PLANTS && state.getMaterial() != Material.VINE
					&& state.getMaterial() != Material.CACTUS) {
				graveyard.generate(worldObj, rand, posGrave);
			}
		}

		if((x > -2 || x < 2) && (z > 6 || z < -1)) {

			BlockPos pos2 = worldObj.getHeight(new BlockPos(k, 0, l));

			//adding RNG to the coords to give a more accurate picture of the actual position
			if(!cityGen.tooClose(pos2.add(rand.nextInt(8) + 8, 0, rand.nextInt(8) + 8)))
				cityGen.generate(worldObj, rand, pos2);

			int randX = k + rand.nextInt(2) + 1;
			int randZ = l + rand.nextInt(2) + 1;

			pos2 = worldObj.getHeight(new BlockPos(randX, 0, randZ));

			if(rand.nextBoolean() && !templeGen.tooClose(pos2) && !cityGen.tooClose(pos2))
				templeGen.generate(worldObj, rand, pos2);


			randX = k + rand.nextInt(8) + 8;
			randZ = l + rand.nextInt(8) + 8;

			pos2 = worldObj.getHeight(new BlockPos(randX, 0, randZ));

			if(rand.nextBoolean() && !towerGen.tooClose(pos2) && !cityGen.tooClose(pos2))
				towerGen.generate(worldObj, rand, pos2);

			randX = k + rand.nextInt(7) + 7;
			randZ = l + rand.nextInt(7) + 7;

			pos2 = worldObj.getHeight(new BlockPos(randX, 0, randZ));

			if(rand.nextBoolean() && !storageGen.tooClose(pos2) && !cityGen.tooClose(pos2))
				storageGen.generate(worldObj, rand, pos2);
		}

		Biome.decorate(worldObj, worldObj.rand, new BlockPos(k, 0, l));

		BlockFalling.fallInstantly = false;
	}

	@Override
	public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, BlockPos pos)
	{
		Biome Biome = worldObj.getBiome(pos);
		return Biome == null ? null : Biome.getSpawnableList(par1EnumCreatureType);
	}

	@Override
	public BlockPos getNearestStructurePos(World par1World, String par2String, BlockPos pos, boolean bool)
	{
		return null;
	}

	@Override
	public void recreateStructures(Chunk chunk, int x, int z) {

	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) {

		return false;
	}

	@Override
	public boolean isInsideStructure(World p_193414_1_, String p_193414_2_, BlockPos p_193414_3_) {

		return false;
	}
}
