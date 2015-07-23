/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
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

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.EntityAbygolem;
import com.shinoow.abyssalcraft.common.entity.EntityDreadguard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiomeGenAbyDreadlands extends BiomeGenDreadlandsBase {

	@SuppressWarnings("unchecked")
	public BiomeGenAbyDreadlands(int par1) {
		super(par1);
		topBlock = AbyssalCraft.abydreadstone;
		fillerBlock = AbyssalCraft.abydreadstone;
		spawnableCreatureList.add(new SpawnListEntry(EntityAbygolem.class, 100, 3, 8));
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadguard.class, 1, 1, 1));
	}

	@Override
	public void decorate(World par1World, Random par2Random, int par3, int par4)
	{
		super.decorate(par1World, par2Random, par3, par4);

		for(int rarity = 0; rarity < 8; rarity++) {
			int veinSize =  2 + par2Random.nextInt(4);
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(55);
			int z = par4 + par2Random.nextInt(16);

			new WorldGenMinable(AbyssalCraft.abydreadore, veinSize, AbyssalCraft.dreadstone).generate(par1World, par2Random, x, y, z);
		}
		for (int rarity = 0; rarity < 7; ++rarity)
		{
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(64);
			int z = par4 + par2Random.nextInt(16);
			new WorldGenMinable(AbyssalCraft.abydreadstone, 64,
					AbyssalCraft.dreadstone).generate(par1World, par2Random, x, y, z);
		}
		for (int rarity = 0; rarity < 7; ++rarity)
		{
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(64);
			int z = par4 + par2Random.nextInt(16);
			new WorldGenMinable(AbyssalCraft.abydreadstone, 48,
					AbyssalCraft.dreadstone).generate(par1World, par2Random, x, y, z);
		}
		for (int rarity = 0; rarity < 7; ++rarity)
		{
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(64);
			int z = par4 + par2Random.nextInt(16);
			new WorldGenMinable(AbyssalCraft.abydreadstone, 32,
					AbyssalCraft.dreadstone).generate(par1World, par2Random, x, y, z);
		}
		for(int rarity = 0; rarity < 8; rarity++) {
			int veinSize =  2 + par2Random.nextInt(4);
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(55);
			int z = par4 + par2Random.nextInt(16);

			new WorldGenMinable(AbyssalCraft.abydreadore, veinSize, AbyssalCraft.abydreadstone).generate(par1World, par2Random, x, y, z);
		}
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
