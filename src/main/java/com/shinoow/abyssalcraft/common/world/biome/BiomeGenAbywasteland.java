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

import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.entity.EntityDragonMinion;
import com.shinoow.abyssalcraft.common.entity.EntitySkeletonGoliath;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiomeGenAbywasteland extends BiomeGenBase {

	@SuppressWarnings("unchecked")
	public BiomeGenAbywasteland(int par1){
		super(par1);
		waterColorMultiplier = 0x24FF83;
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 50, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 50, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntityDepthsGhoul.class, 60, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntityAbyssalZombie.class, 60, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntitySkeletonGoliath.class, 15, 1, 1));
		spawnableMonsterList.add(new SpawnListEntry(EntityDragonMinion.class, 3, 1, 1));
	}

	@Override
	public void decorate(World par1World, Random par2Random, int par3, int par4){
		super.decorate(par1World, par2Random, par3, par4);
		int var5 = 3 + par2Random.nextInt(6);

		for (int rarity = 0; rarity < 8; rarity++){
			int veinSize = 1 + par2Random.nextInt(3);
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(18) + 4;
			int z = par4 + par2Random.nextInt(16);

			new WorldGenMinable(AbyssalCraft.AbyLCorOre, veinSize, AbyssalCraft.abystone).generate(par1World, par2Random, x, y, z);
		}
		for(int rarity = 0; rarity < 8; rarity++) {
			int veinSize =  2 + par2Random.nextInt(6);
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(55);
			int z = par4 + par2Random.nextInt(16);

			new WorldGenMinable(AbyssalCraft.AbyCorOre, veinSize, AbyssalCraft.abystone).generate(par1World, par2Random, x, y, z);
		}
		for(int rarity = 0; rarity < 8; rarity++) {
			int veinSize =  2 + par2Random.nextInt(6);
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(40);
			int z = par4 + par2Random.nextInt(16);

			new WorldGenMinable(AbyssalCraft.AbyNitOre, veinSize, AbyssalCraft.abystone).generate(par1World, par2Random, x, y, z);
		}
		for(int rarity = 0; rarity < 8; rarity++) {
			int veinSize = 2 + par2Random.nextInt(6);
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(40);
			int z = par4 + par2Random.nextInt(16);

			new WorldGenMinable(AbyssalCraft.AbyIroOre, veinSize, AbyssalCraft.abystone).generate(par1World, par2Random, x, y, z);
		}
		for(int rarity = 0; rarity < 8; rarity++) {
			int veinSize = 2 + par2Random.nextInt(6);
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(40);
			int z = par4 + par2Random.nextInt(16);

			new WorldGenMinable(AbyssalCraft.AbyCopOre, veinSize, AbyssalCraft.abystone).generate(par1World, par2Random, x, y, z);
		}
		for(int rarity = 0; rarity < 5; rarity++) {
			int veinSize = 2 + par2Random.nextInt(3);
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(20);
			int z = par4 + par2Random.nextInt(16);

			new WorldGenMinable(AbyssalCraft.AbyGolOre, veinSize, AbyssalCraft.abystone).generate(par1World, par2Random, x, y, z);
		}
		for(int rarity = 0; rarity < var5; rarity++) {
			int veinSize = 1 + par2Random.nextInt(7);
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(10);
			int z = par4 + par2Random.nextInt(16);

			new WorldGenMinable(AbyssalCraft.AbyDiaOre, veinSize, AbyssalCraft.abystone).generate(par1World, par2Random, x, y, z);
		}
		for(int rarity = 0; rarity < var5; rarity++) {
			int veinSize = 1 + par2Random.nextInt(3);
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(10);
			int z = par4 + par2Random.nextInt(16);

			new WorldGenMinable(AbyssalCraft.AbyPCorOre, veinSize, AbyssalCraft.abystone).generate(par1World, par2Random, x, y, z);
		}
		for(int rarity = 0; rarity < 8; rarity++) {
			int veinSize = 2 + par2Random.nextInt(6);
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(40);
			int z = par4 + par2Random.nextInt(16);

			new WorldGenMinable(AbyssalCraft.AbyTinOre, veinSize, AbyssalCraft.abystone).generate(par1World, par2Random, x, y, z);
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
	public int getBiomeGrassColor(int par1, int par2, int par3)
	{
		return 0x6EF5DE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeFoliageColor(int par1, int par2, int par3)
	{
		return 0x6EF5DE;
	}
}
