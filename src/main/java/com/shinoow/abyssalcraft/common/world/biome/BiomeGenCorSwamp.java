/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.common.world.biome;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsghoul;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenAntimatterLake;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiomeGenCorSwamp extends BiomeGenBase {

	@SuppressWarnings("unchecked")
	public BiomeGenCorSwamp(int par1) {
		super(par1);
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.rootHeight = -0.2F;
		this.heightVariation = 0.1F;
		this.topBlock=Blocks.grass;
		this.fillerBlock=Blocks.dirt;
		this.theBiomeDecorator.treesPerChunk = 2;
		this.theBiomeDecorator.flowersPerChunk = 1;
		this.theBiomeDecorator.deadBushPerChunk = 1;
		this.theBiomeDecorator.mushroomsPerChunk = 8;
		this.theBiomeDecorator.reedsPerChunk = 10;
		this.theBiomeDecorator.clayPerChunk = 1;
		this.theBiomeDecorator.waterlilyPerChunk = 4;
		this.theBiomeDecorator.sandPerChunk2 = 0;
		this.theBiomeDecorator.sandPerChunk = 0;
		this.theBiomeDecorator.grassPerChunk = 5;
		this.waterColorMultiplier = 0x24FF83;
		this.spawnableMonsterList.add(new SpawnListEntry(EntityDepthsghoul.class, 5, 1, 5));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityDepthsZombie.class, 5, 1, 5));
	}

	public void decorate(World par1World, Random par2Random, int par3, int par4)
	{
		super.decorate(par1World, par2Random, par3, par4);
		int var5 = 3 + par2Random.nextInt(6);
		int var6;
		int var7;
		int var8;

		for (var6 = 0; var6 < var5; ++var6)
		{
			var7 = par3 + par2Random.nextInt(16);
			var8 = par2Random.nextInt(28) + 4;
			int var9 = par4 + par2Random.nextInt(16);
			Block var10 = par1World.getBlock(var7, var8, var9);

			if (var10 == Blocks.stone || var10 == Blocks.iron_ore || var10 == Blocks.coal_ore)
			{
				par1World.setBlock(var7, var8, var9, AbyssalCraft.Coraliumore);
			}
		}
		for(int rarity = 0; rarity < 3; rarity++)
		{
			int veinSize = 4;
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(40);
			int z = par4 + par2Random.nextInt(16);

			new WorldGenMinable(AbyssalCraft.Coraliumore, veinSize).generate(par1World, par2Random, x, y, z);
		}

		for(int k = 0; k < 2; k++)
		{
			int RandPosX = par3 + par2Random.nextInt(5);
			int RandPosY = par2Random.nextInt(60);
			int RandPosZ = par4 + par2Random.nextInt(5);
			(new WorldGenAntimatterLake(AbyssalCraft.anticwater)).generate(par1World, par2Random, RandPosX, RandPosY, RandPosZ);	
		}
	}

	public WorldGenAbstractTree func_150567_a(Random p_150567_1_)
	{
		return this.worldGeneratorSwamp;
	}

	/**
	 * Provides the basic grass color based on the biome temperature and rainfall
	 */
	@SideOnly(Side.CLIENT)
	public int getBiomeGrassColor(int p_150558_1_, int p_150558_2_, int p_150558_3_)
	{
		return 0x6EF5DE;
	}

	/**
	 * Provides the basic foliage color based on the biome temperature and rainfall
	 */
	@SideOnly(Side.CLIENT)
	public int getBiomeFoliageColor(int p_150571_1_, int p_150571_2_, int p_150571_3_)
	{
		return 0x6EF5DE;
	}
}
