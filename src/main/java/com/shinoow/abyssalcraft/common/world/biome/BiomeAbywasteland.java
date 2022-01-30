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
package com.shinoow.abyssalcraft.common.world.biome;

import java.util.Random;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.lib.ACClientVars;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenBush;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeAbywasteland extends Biome {

	public BiomeAbywasteland(BiomeProperties par1){
		super(par1);
		topBlock = ACBlocks.fused_abyssal_sand.getDefaultState();
		fillerBlock = ACBlocks.abyssal_sand.getDefaultState();
		setMobSpawns();
	}

	public final void setMobSpawns(){
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		spawnableWaterCreatureList.add(new SpawnListEntry(EntityCoraliumSquid.class, 10, 4, 4));
		spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 50, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 50, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntityDepthsGhoul.class, 60, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntityAbyssalZombie.class, 60, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntitySkeletonGoliath.class, 15, 1, 1));
		if(!ACConfig.no_spectral_dragons)
			spawnableMonsterList.add(new SpawnListEntry(EntityDragonMinion.class, 1, 0, 1));
	}

	@Override
	public void decorate(World par1World, Random par2Random, BlockPos pos){
		for (int l3 = 0; l3 < 4; ++l3)
		{
			if (par2Random.nextInt(4) == 0)
			{
				int i4 = par2Random.nextInt(16) + 8;
				int k8 = par2Random.nextInt(16) + 8;
				int j12 = par1World.getHeight(pos.add(i4, 0, k8)).getY() * 2;

				if (j12 > 0)
				{
					int k15 = par2Random.nextInt(j12);
					new WorldGenBush((BlockBush)ACBlocks.luminous_thistle).generate(par1World, par2Random, pos.add(i4, k15, k8));
				}
			}

			if (par2Random.nextInt(8) == 0)
			{
				int j4 = par2Random.nextInt(16) + 8;
				int l8 = par2Random.nextInt(16) + 8;
				int k12 = par1World.getHeight(pos.add(j4, 0, l8)).getY() * 2;

				if (k12 > 0)
				{
					int l15 = par2Random.nextInt(k12);
					new WorldGenBush((BlockBush)ACBlocks.wastelands_thorn).generate(par1World, par2Random, pos.add(j4, l15, l8));
				}
			}
		}
		if (par2Random.nextInt(4) == 0)
		{
			int i4 = par2Random.nextInt(16) + 8;
			int k8 = par2Random.nextInt(16) + 8;
			int j12 = par1World.getHeight(pos.add(i4, 0, k8)).getY() * 2;

			if (j12 > 0)
			{
				int k15 = par2Random.nextInt(j12);
				new WorldGenBush((BlockBush)ACBlocks.luminous_thistle).generate(par1World, par2Random, pos.add(i4, k15, k8));
			}
		}
		if (par2Random.nextInt(8) == 0)
		{
			int j4 = par2Random.nextInt(16) + 8;
			int l8 = par2Random.nextInt(16) + 8;
			int k12 = par1World.getHeight(pos.add(j4, 0, l8)).getY() * 2;

			if (k12 > 0)
			{
				int l15 = par2Random.nextInt(k12);
				new WorldGenBush((BlockBush)ACBlocks.wastelands_thorn).generate(par1World, par2Random, pos.add(j4, l15, l8));
			}
		}

		int var5 = 3 + par2Random.nextInt(6);

		if(ACConfig.generateLiquifiedCoraliumOre)
			for (int rarity = 0; rarity < 8; rarity++){
				int veinSize = 1 + par2Random.nextInt(4);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(30) + 5;
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.liquified_coralium_ore.getDefaultState(), veinSize,
						state -> state != null && state == ACBlocks.abyssal_stone.getDefaultState()).generate(par1World, par2Random, pos.add(x, y, z));
			}
		if(ACConfig.generateAbyssalCoraliumOre)
			for(int rarity = 0; rarity < var5 + 5 + par2Random.nextInt(3); rarity++) {
				int veinSize =  2 + par2Random.nextInt(8);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(75) + 5;
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.abyssal_coralium_ore.getDefaultState(), veinSize,
						state -> state != null && state == ACBlocks.abyssal_stone.getDefaultState()).generate(par1World, par2Random, pos.add(x, y, z));
			}
		if(ACConfig.generateAbyssalNitreOre)
			for(int rarity = 0; rarity < 8; rarity++) {
				int veinSize =  2 + par2Random.nextInt(6);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(60) + 5;
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.abyssal_nitre_ore.getDefaultState(), veinSize,
						state -> state != null && state == ACBlocks.abyssal_stone.getDefaultState()).generate(par1World, par2Random, pos.add(x, y, z));
			}
		if(ACConfig.generateAbyssalIronOre)
			for(int rarity = 0; rarity < 8; rarity++) {
				int veinSize = 2 + par2Random.nextInt(6);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(60) + 5;
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.abyssal_iron_ore.getDefaultState(), veinSize,
						state -> state != null && state == ACBlocks.abyssal_stone.getDefaultState()).generate(par1World, par2Random, pos.add(x, y, z));
			}
		if(ACConfig.generateAbyssalCopperOre)
			for(int rarity = 0; rarity < 8; rarity++) {
				int veinSize = 2 + par2Random.nextInt(6);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(60) + 5;
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.abyssal_copper_ore.getDefaultState(), veinSize,
						state -> state != null && state == ACBlocks.abyssal_stone.getDefaultState()).generate(par1World, par2Random, pos.add(x, y, z));
			}
		if(ACConfig.generateAbyssalGoldOre)
			for(int rarity = 0; rarity < 5; rarity++) {
				int veinSize = 2 + par2Random.nextInt(3);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(35) + 5;
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.abyssal_gold_ore.getDefaultState(), veinSize,
						state -> state != null && state == ACBlocks.abyssal_stone.getDefaultState()).generate(par1World, par2Random, pos.add(x, y, z));
			}
		if(ACConfig.generateAbyssalDiamondOre)
			for(int rarity = 0; rarity < 5; rarity++) {
				int veinSize = 1 + par2Random.nextInt(4);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(20) + 5;
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.abyssal_diamond_ore.getDefaultState(), veinSize,
						state -> state != null && state == ACBlocks.abyssal_stone.getDefaultState()).generate(par1World, par2Random, pos.add(x, y, z));
			}
		if(ACConfig.generatePearlescentCoraliumOre)
			for(int rarity = 0; rarity < var5; rarity++) {
				int veinSize = 1 + par2Random.nextInt(4);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(15) + 5;
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.pearlescent_coralium_ore.getDefaultState(), veinSize,
						state -> state != null && state == ACBlocks.abyssal_stone.getDefaultState()).generate(par1World, par2Random, pos.add(x, y, z));
			}
		if(ACConfig.generateAbyssalTinOre)
			for(int rarity = 0; rarity < 8; rarity++) {
				int veinSize = 2 + par2Random.nextInt(6);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(60) + 5;
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.abyssal_tin_ore.getDefaultState(), veinSize,
						state -> state != null && state == ACBlocks.abyssal_stone.getDefaultState()).generate(par1World, par2Random, pos.add(x, y, z));
			}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float par1)
	{
		return ACClientVars.getAbyssalWastelandSkyColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getGrassColorAtPos(BlockPos pos)
	{
		return ACClientVars.getAbyssalWastelandGrassColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return ACClientVars.getAbyssalWastelandFoliageColor();
	}

	@Override
	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int p_180622_4_, int p_180622_5_, double p_180622_6_)
	{
		generateAbyssalWastelandTerrain(worldIn, rand, chunkPrimerIn, p_180622_4_, p_180622_5_, p_180622_6_);
	}

	public final void generateAbyssalWastelandTerrain(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int p_180628_4_, int p_180628_5_, double p_180628_6_)
	{
		int i = worldIn.getSeaLevel();
		IBlockState iblockstate = topBlock;
		IBlockState iblockstate1 = fillerBlock;
		int j = -1;
		int k = (int)(p_180628_6_ / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		int l = p_180628_4_ & 15;
		int i1 = p_180628_5_ & 15;

		for (int j1 = 255; j1 >= 0; --j1)
			if (j1 < 7)
			{
				if(j1 == 0)
					chunkPrimerIn.setBlockState(i1, j1, l, Blocks.BEDROCK.getDefaultState());
				else if(j1 == 6)
					chunkPrimerIn.setBlockState(i1, j1, l, ACBlocks.darkstone_cobblestone.getDefaultState());
				else if(j1 == 1)
					chunkPrimerIn.setBlockState(i1, j1, l, ACBlocks.darkstone.getDefaultState());
				else {
					IBlockState state = Blocks.AIR.getDefaultState();

					if(i1 % 4 == 2 && l % 4 == 2)
						state = (j1 == 4 ? ACBlocks.chiseled_darkstone_brick : ACBlocks.darkstone_brick).getDefaultState();
					chunkPrimerIn.setBlockState(i1, j1, l, state);

				}
			}
			else
			{
				IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);

				if (iblockstate2.getMaterial() == Material.AIR)
					j = -1;
				else if (iblockstate2 == ACBlocks.abyssal_stone.getDefaultState())
					if (j == -1)
					{
						if (k <= 0)
						{
							iblockstate = null;
							iblockstate1 = ACBlocks.abyssal_stone.getDefaultState();
						}
						else if (j1 >= i - 4 && j1 <= i + 1)
						{
							iblockstate = topBlock;
							iblockstate1 = fillerBlock;
						}

						j = k;

						if (j1 >= i - 1)
							chunkPrimerIn.setBlockState(i1, j1, l, iblockstate);
						else if (j1 < i - 7 - k)
						{
							iblockstate = null;
							iblockstate1 = ACBlocks.abyssal_stone.getDefaultState();
							chunkPrimerIn.setBlockState(i1, j1, l, ACBlocks.abyssal_stone.getDefaultState());
						} else
							chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
					}
					else if (j > 0)
					{
						--j;
						chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
					}
			}
	}
}
