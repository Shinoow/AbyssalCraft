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
package com.shinoow.abyssalcraft.common.world.biome;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.biome.IDreadlandsBiome;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.EntityAbygolem;
import com.shinoow.abyssalcraft.common.entity.EntityChagarothFist;
import com.shinoow.abyssalcraft.common.entity.EntityDreadSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityDreadgolem;
import com.shinoow.abyssalcraft.common.entity.EntityDreadguard;
import com.shinoow.abyssalcraft.common.entity.EntityDreadling;
import com.shinoow.abyssalcraft.common.entity.EntityGreaterDreadSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityLesserDreadbeast;
import com.shinoow.abyssalcraft.common.entity.demon.EntityDemonPig;

public class BiomeGenDreadlandsBase extends Biome implements IDreadlandsBiome {

	@SuppressWarnings("unchecked")
	public BiomeGenDreadlandsBase(BiomeProperties par1) {
		super(par1);
		topBlock = ACBlocks.dreadstone.getDefaultState();
		fillerBlock = ACBlocks.dreadstone.getDefaultState();
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		theBiomeDecorator.treesPerChunk = -1;
		theBiomeDecorator.flowersPerChunk= -1;
		spawnableCreatureList.add(new SpawnListEntry(EntityAbygolem.class, 60, 1, 3));
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadgolem.class, 60, 1, 3));
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadSpawn.class, 30, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadling.class, 40, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityChagarothFist.class, 2, 1, 1));
		spawnableMonsterList.add(new SpawnListEntry(EntityDemonPig.class, 5, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityGreaterDreadSpawn.class, 5, 1, 1));
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadguard.class, 8, 1, 1));
		spawnableMonsterList.add(new SpawnListEntry(EntityLesserDreadbeast.class, 1, 0, 1));
	}

	@Override
	public void decorate(World par1World, Random par2Random, BlockPos pos)
	{
		super.decorate(par1World, par2Random, pos);

		if(AbyssalCraft.generateDreadedAbyssalniteOre)
			for(int rarity = 0; rarity < 8; rarity++) {
				int veinSize =  4 + par2Random.nextInt(12);
				int x = par2Random.nextInt(16) + 8;
				int y = par2Random.nextInt(60);
				int z = par2Random.nextInt(16) + 8;

				new WorldGenMinable(ACBlocks.dreaded_abyssalnite_ore.getDefaultState(), veinSize, BlockMatcher.forBlock(ACBlocks.dreadstone)).generate(par1World, par2Random, pos.add(x, y, z));
			}

		for (int rarity = 0; rarity < 3; ++rarity)
		{
			int x = par2Random.nextInt(16) + 8;
			int y = par2Random.nextInt(55);
			int z = par2Random.nextInt(16) + 8;
			new WorldGenMinable(ACBlocks.abyssalnite_stone.getDefaultState(), 16,
					BlockMatcher.forBlock(ACBlocks.dreadstone)).generate(par1World, par2Random, pos.add(x, y, z));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float par1)
	{
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getGrassColorAtPos(BlockPos pos)
	{
		return 0x910000;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return 0x910000;
	}

	@Override
	public void genTerrainBlocks(World world, Random rand, ChunkPrimer chunkPrimer, int x, int z, double d)
	{
		genDreadlandsTerrain(world, rand, chunkPrimer, x, z, d);
	}

	public final void genDreadlandsTerrain(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double d)
	{
		int i = worldIn.getSeaLevel();
		IBlockState iblockstate = topBlock;
		IBlockState iblockstate1 = fillerBlock;
		int j = -1;
		int k = (int)(d / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		int l = x & 15;
		int i1 = z & 15;
		new BlockPos.MutableBlockPos();

		for (int j1 = 255; j1 >= 0; --j1)
			if (j1 <= rand.nextInt(5))
				chunkPrimerIn.setBlockState(i1, j1, l, Blocks.BEDROCK.getDefaultState());
			else
			{
				IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);

				if (iblockstate2.getMaterial() == Material.AIR)
					j = -1;
				else if (iblockstate2.getBlock() == ACBlocks.dreadstone)
					if (j == -1)
					{
						if (k <= 0)
						{
							iblockstate = null;
							iblockstate1 = ACBlocks.dreadstone.getDefaultState();
						}
						else if (j1 >= i - 4 && j1 <= i + 1)
						{
							iblockstate = topBlock;
							iblockstate1 = fillerBlock;
						}

						//						if (j1 < i && (iblockstate == null || iblockstate.getBlock().getMaterial() == Material.air))
						//							if (getFloatTemperature(blockpos$mutableblockpos.set(x, j1, z)) < 0.15F)
						//								iblockstate = Blocks.ice.getDefaultState();
						//							else
						//								iblockstate = Blocks.water.getDefaultState();

						j = k;

						if (j1 >= i - 1)
							chunkPrimerIn.setBlockState(i1, j1, l, iblockstate);
						else if (j1 < i - 7 - k)
						{
							iblockstate = null;
							iblockstate1 = ACBlocks.dreadstone.getDefaultState();
							chunkPrimerIn.setBlockState(i1, j1, l, ACBlocks.dreadstone.getDefaultState());
						} else
							chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
					}
					else if (j > 0)
					{
						--j;
						chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);

						//						if (j == 0 && iblockstate1.getBlock() == Blocks.sand)
						//						{
						//							j = rand.nextInt(4) + Math.max(0, j1 - 63);
						//							iblockstate1 = iblockstate1.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND ? Blocks.red_sandstone.getDefaultState() : Blocks.sandstone.getDefaultState();
						//						}
					}
			}
	}
}