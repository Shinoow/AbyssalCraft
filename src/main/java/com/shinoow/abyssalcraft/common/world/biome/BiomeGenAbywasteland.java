/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.world.biome;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.monster.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.*;

import cpw.mods.fml.relauncher.*;

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

		for (int rarity = 0; rarity < var5; ++rarity){
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(18) + 4;
			int z = par4 + par2Random.nextInt(16);
			Block var10 = par1World.getBlock(x, y, z);

			if (var10 == AbyssalCraft.abystone)
				par1World.setBlock(x, y, z, AbyssalCraft.AbyLCorOre);
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
			int veinSize = 1 + par2Random.nextInt(3);
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