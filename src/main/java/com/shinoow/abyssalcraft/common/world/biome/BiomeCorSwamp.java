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
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.entity.anti.*;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenAntimatterLake;
import com.shinoow.abyssalcraft.lib.ACClientVars;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeCorSwamp extends Biome {

	public BiomeCorSwamp(BiomeProperties par1) {
		super(par1);
		topBlock=Blocks.GRASS.getDefaultState();
		fillerBlock=Blocks.DIRT.getDefaultState();
		decorator.treesPerChunk = 2;
		decorator.flowersPerChunk = 1;
		decorator.deadBushPerChunk = 1;
		decorator.mushroomsPerChunk = 8;
		decorator.reedsPerChunk = 10;
		decorator.clayPerChunk = 1;
		decorator.waterlilyPerChunk = 4;
		decorator.sandPatchesPerChunk = 0;
		decorator.gravelPatchesPerChunk = 0;
		decorator.grassPerChunk = 5;
		spawnableMonsterList.add(new SpawnListEntry(EntityDepthsGhoul.class, 60, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntityAbyssalZombie.class, 60, 1, 5));
		spawnableCreatureList.add(new SpawnListEntry(EntityAntiPig.class, 5, 1, 2));
		spawnableCaveCreatureList.add(new SpawnListEntry(EntityAntiBat.class, 10, 1, 2));
		spawnableCreatureList.add(new SpawnListEntry(EntityAntiChicken.class, 5, 1, 2));
		spawnableCreatureList.add(new SpawnListEntry(EntityAntiCow.class, 5, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityAntiAbyssalZombie.class, 5, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityAntiCreeper.class, 5, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityAntiGhoul.class, 5, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityAntiPlayer.class, 5, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityAntiSkeleton.class, 5, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityAntiSpider.class, 5, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityAntiZombie.class, 5, 1, 2));
	}

	@Override
	public void decorate(World par1World, Random par2Random, BlockPos pos)
	{
		super.decorate(par1World, par2Random, pos);
		int var5 = 3 + par2Random.nextInt(6);

		if(ACConfig.generateCoraliumOre){
			for (int var6 = 0; var6 < var5; var6++)
			{
				int var7 = par2Random.nextInt(16);
				int var8 = par2Random.nextInt(28) + 4;
				int var9 = par2Random.nextInt(16);
				Block var10 = par1World.getBlockState(pos.add(var7, var8, var9)).getBlock();

				if (var10 == Blocks.IRON_ORE || var10 == Blocks.COAL_ORE)
					par1World.setBlockState(pos.add(var7, var8, var9), ACBlocks.coralium_ore.getDefaultState());
			}
			for(int rarity = 0; rarity < 9; rarity++)
			{
				int veinSize = 4 + par2Random.nextInt(2);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(63);
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.coralium_ore.getDefaultState(), veinSize).generate(par1World, par2Random, pos.add(x, y, z));
			}
		}

		if(ACConfig.generateAntimatterLake)
			for(int k = 0; k < 1; k++)
			{
				int RandPosX = par2Random.nextInt(16) + 8;
				int RandPosY = par2Random.nextInt(60);
				int RandPosZ = par2Random.nextInt(16) + 8;
				if(par2Random.nextInt(10) == 0)
					new WorldGenAntimatterLake(ACBlocks.liquid_antimatter).generate(par1World, par2Random, pos.add(RandPosX, RandPosY, RandPosZ));
			}
	}

	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random par1Random)
	{
		return SWAMP_FEATURE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getGrassColorAtPos(BlockPos pos)
	{
		return ACClientVars.getCoraliumInfestedSwampGrassColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return ACClientVars.getCoraliumInfestedSwampFoliageColor();
	}
}
