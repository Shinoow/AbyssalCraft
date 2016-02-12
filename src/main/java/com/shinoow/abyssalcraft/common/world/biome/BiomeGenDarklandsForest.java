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

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenTrees;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenDLT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiomeGenDarklandsForest extends BiomeGenBase {

	private WorldGenTrees WorldGenDarkTrees;

	@SuppressWarnings("unchecked")
	public BiomeGenDarklandsForest(int par1)
	{
		super(par1);
		topBlock = AbyssalCraft.Darkgrass;
		fillerBlock = Blocks.dirt;
		waterColorMultiplier = 14745518;
		WorldGenDarkTrees = new WorldGenDLT(false);
		theBiomeDecorator.treesPerChunk = 20;
		spawnableMonsterList.add(new SpawnListEntry(EntityDepthsGhoul.class, 60, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntityAbyssalZombie.class, 60, 1, 3));
	}

	@Override
	public void decorate(World par1World, Random par2Random, int par3, int par4)
	{
		super.decorate(par1World, par2Random, par3, par4);
		int var5 = 3 + par2Random.nextInt(6);

		if(AbyssalCraft.generateAbyssalniteOre)
			for (int rarity = 0; rarity < var5; ++rarity)
			{
				int veinSize = 1 + par2Random.nextInt(3);
				int x = par3 + par2Random.nextInt(16);
				int y = par2Random.nextInt(28) + 4;
				int z = par4 + par2Random.nextInt(16);

				new WorldGenMinable(AbyssalCraft.abyore, veinSize).generate(par1World, par2Random, x, y, z);
			}

		for (int rarity = 0; rarity < 7; ++rarity)
		{
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(64);
			int z = par4 + par2Random.nextInt(16);
			new WorldGenMinable(AbyssalCraft.Darkstone, 20).generate(par1World, par2Random, x, y, z);
		}

		for (int rarity = 0; rarity < 7; ++rarity)
		{
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(64);
			int z = par4 + par2Random.nextInt(16);
			new WorldGenMinable(AbyssalCraft.abydreadstone, 1).generate(par1World, par2Random, x, y, z);
		}
	}

	@Override
	public WorldGenAbstractTree func_150567_a(Random par1Random)
	{
		return par1Random.nextInt(5) == 0 ? worldGeneratorTrees : par1Random.nextInt(10) == 0 ? WorldGenDarkTrees : worldGeneratorTrees;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float par1)
	{
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeGrassColor(int par1, int par2, int par3)
	{
		return 0x30217A;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeFoliageColor(int par1, int par2, int par3)
	{
		return 0x30217A;
	}
}
