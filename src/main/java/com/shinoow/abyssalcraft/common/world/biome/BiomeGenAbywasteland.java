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

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.entity.EntityDragonMinion;
import com.shinoow.abyssalcraft.common.entity.EntitySkeletonGoliath;

public class BiomeGenAbywasteland extends BiomeGenBase {

	@SuppressWarnings("unchecked")
	public BiomeGenAbywasteland(BiomeProperties par1){
		super(par1);
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 50, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 50, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntityDepthsGhoul.class, 60, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntityAbyssalZombie.class, 60, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntitySkeletonGoliath.class, 15, 1, 1));
		spawnableMonsterList.add(new SpawnListEntry(EntityDragonMinion.class, 1, 0, 1));
	}

	@Override
	public void decorate(World par1World, Random par2Random, BlockPos pos){
		super.decorate(par1World, par2Random, pos);
		int var5 = 3 + par2Random.nextInt(6);

		if(AbyssalCraft.generateLiquifiedCoraliumOre)
			for (int rarity = 0; rarity < 8; rarity++){
				int veinSize = 1 + par2Random.nextInt(3);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(30);
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.liquified_coralium_ore.getDefaultState(), veinSize, BlockMatcher.forBlock(ACBlocks.abyssal_stone)).generate(par1World, par2Random, pos.add(x, y, z));
			}
		if(AbyssalCraft.generateAbyssalCoraliumOre)
			for(int rarity = 0; rarity < 8; rarity++) {
				int veinSize =  2 + par2Random.nextInt(6);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(75);
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.abyssal_coralium_ore.getDefaultState(), veinSize, BlockMatcher.forBlock(ACBlocks.abyssal_stone)).generate(par1World, par2Random, pos.add(x, y, z));
			}
		if(AbyssalCraft.generateAbyssalNitreOre)
			for(int rarity = 0; rarity < 8; rarity++) {
				int veinSize =  2 + par2Random.nextInt(6);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(60);
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.abyssal_nitre_ore.getDefaultState(), veinSize, BlockMatcher.forBlock(ACBlocks.abyssal_stone)).generate(par1World, par2Random, pos.add(x, y, z));
			}
		if(AbyssalCraft.generateAbyssalIronOre)
			for(int rarity = 0; rarity < 8; rarity++) {
				int veinSize = 2 + par2Random.nextInt(6);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(60);
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.abyssal_iron_ore.getDefaultState(), veinSize, BlockMatcher.forBlock(ACBlocks.abyssal_stone)).generate(par1World, par2Random, pos.add(x, y, z));
			}
		if(AbyssalCraft.generateAbyssalCopperOre)
			for(int rarity = 0; rarity < 8; rarity++) {
				int veinSize = 2 + par2Random.nextInt(6);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(60);
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.abyssal_copper_ore.getDefaultState(), veinSize, BlockMatcher.forBlock(ACBlocks.abyssal_stone)).generate(par1World, par2Random, pos.add(x, y, z));
			}
		if(AbyssalCraft.generateAbyssalGoldOre)
			for(int rarity = 0; rarity < 5; rarity++) {
				int veinSize = 2 + par2Random.nextInt(3);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(35);
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.abyssal_gold_ore.getDefaultState(), veinSize, BlockMatcher.forBlock(ACBlocks.abyssal_stone)).generate(par1World, par2Random, pos.add(x, y, z));
			}
		if(AbyssalCraft.generateAbyssalDiamondOre)
			for(int rarity = 0; rarity < var5; rarity++) {
				int veinSize = 1 + par2Random.nextInt(7);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(20);
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.abyssal_diamond_ore.getDefaultState(), veinSize, BlockMatcher.forBlock(ACBlocks.abyssal_stone)).generate(par1World, par2Random, pos.add(x, y, z));
			}
		if(AbyssalCraft.generatePearlescentCoraliumOre)
			for(int rarity = 0; rarity < var5; rarity++) {
				int veinSize = 1 + par2Random.nextInt(3);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(15);
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.pearlescent_coralium_ore.getDefaultState(), veinSize, BlockMatcher.forBlock(ACBlocks.abyssal_stone)).generate(par1World, par2Random, pos.add(x, y, z));
			}
		if(AbyssalCraft.generateAbyssalTinOre)
			for(int rarity = 0; rarity < 8; rarity++) {
				int veinSize = 2 + par2Random.nextInt(6);
				int x = par2Random.nextInt(16);
				int y = par2Random.nextInt(60);
				int z = par2Random.nextInt(16);

				new WorldGenMinable(ACBlocks.abyssal_tin_ore.getDefaultState(), veinSize, BlockMatcher.forBlock(ACBlocks.abyssal_stone)).generate(par1World, par2Random, pos.add(x, y, z));
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
		return 0x6EF5DE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return 0x6EF5DE;
	}
}
