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

import net.minecraft.block.Block;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiBat;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiChicken;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiCow;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiCreeper;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiGhoul;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiPig;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiPlayer;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiSkeleton;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiSpider;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiZombie;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenAntimatterLake;

public class BiomeGenCorSwamp extends BiomeGenBase {

	@SuppressWarnings("unchecked")
	public BiomeGenCorSwamp(int par1) {
		super(par1);
		minHeight = -0.2F;
		maxHeight = 0.1F;
		topBlock=Blocks.grass.getDefaultState();
		fillerBlock=Blocks.dirt.getDefaultState();
		theBiomeDecorator.treesPerChunk = 2;
		theBiomeDecorator.flowersPerChunk = 1;
		theBiomeDecorator.deadBushPerChunk = 1;
		theBiomeDecorator.mushroomsPerChunk = 8;
		theBiomeDecorator.reedsPerChunk = 10;
		theBiomeDecorator.clayPerChunk = 1;
		theBiomeDecorator.waterlilyPerChunk = 4;
		theBiomeDecorator.sandPerChunk2 = 0;
		theBiomeDecorator.sandPerChunk = 0;
		theBiomeDecorator.grassPerChunk = 5;
		waterColorMultiplier = 0x24FF83;
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

		if(AbyssalCraft.generateCoraliumOre){
			for (int var6 = 0; var6 < var5; ++var6)
			{
				int var7 = par2Random.nextInt(16);
				int var8 = par2Random.nextInt(28) + 4;
				int var9 = par2Random.nextInt(16);
				Block var10 = par1World.getBlockState(pos.add(var7, var8, var9)).getBlock();

				if (var10 != null && var10.isReplaceableOreGen(par1World, pos.add(var7, var8, var9), BlockHelper.forBlock(Blocks.stone)) || var10 == Blocks.iron_ore || var10 == Blocks.coal_ore)
					par1World.setBlockState(pos.add(var7, var8, var9), ACBlocks.coralium_ore.getDefaultState(), 2);
			}
			for(int rarity = 0; rarity < 6; rarity++)
			{
				int veinSize = 4;
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(40);
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.coralium_ore.getDefaultState(), veinSize).generate(par1World, par2Random, pos.add(x, y, z));
			}
		}

		if(AbyssalCraft.generateAntimatterLake)
			for(int k = 0; k < 1; k++)
			{
				int RandPosX = par2Random.nextInt(16);
				int RandPosY = par2Random.nextInt(60);
				int RandPosZ = par2Random.nextInt(16);
				if(par2Random.nextInt(10) == 0)
					new WorldGenAntimatterLake(ACBlocks.liquid_antimatter).generate(par1World, par2Random, pos.add(RandPosX, RandPosY, RandPosZ));
			}
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(Random par1Random)
	{
		return worldGeneratorSwamp;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getGrassColorAtPos(BlockPos pos)
	{
		return 0x6EF5DE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return 0x6EF5DE;
	}
}
