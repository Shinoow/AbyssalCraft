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
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.*;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiomeGenCorOcean extends BiomeGenBase {

	@SuppressWarnings("unchecked")
	public BiomeGenCorOcean(int par1) {
		super(par1);
		rootHeight = -1.0F;
		heightVariation = 0.1F;
		waterColorMultiplier = 0x24FF83;
		spawnableCreatureList.clear();
		spawnableMonsterList.add(new SpawnListEntry(EntityDepthsGhoul.class, 60, 1, 5));
		spawnableMonsterList.add(new SpawnListEntry(EntityAbyssalZombie.class, 60, 1, 5));

	}

	@Override
	public void decorate(World par1World, Random par2Random, int par3, int par4) {

		super.decorate(par1World, par2Random, par3, par4);
		int var5 = 3 + par2Random.nextInt(6);
		int var6;
		int var7;
		int var8;

		for (var6 = 0; var6 < var5; ++var6) {
			var7 = par3 + par2Random.nextInt(16);
			var8 = par2Random.nextInt(28) + 4;
			int var9 = par4 + par2Random.nextInt(16);
			Block var10 = par1World.getBlock(var7, var8, var9);

			if (var10.isReplaceableOreGen(par1World, var7, var8, var9, Blocks.stone) || var10 == Blocks.iron_ore || var10 == Blocks.coal_ore) {
				par1World.setBlock(var7, var8, var9, AbyssalCraft.Coraliumore);
				if(var10 == Blocks.diamond_ore || var10 == Blocks.gold_ore || var10 == Blocks.iron_ore)
					par1World.setBlock(var7, var8, var9, AbyssalCraft.CoraliumInfusedStone);
			}
		}
		for(int rarity = 0; rarity < 3; rarity++) {
			int veinSize = 4;
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(40);
			int z = par4 + par2Random.nextInt(16);

			new WorldGenMinable(AbyssalCraft.Coraliumore, veinSize).generate(par1World, par2Random, x, y, z);
		}
	}

	@Override
	public BiomeGenBase.TempCategory getTempCategory()
	{
		return BiomeGenBase.TempCategory.OCEAN;
	}

	@Override
	public void genTerrainBlocks(World par1World, Random par2Random, Block[] par3BlockArray, byte[] par4ByteArray, int par5, int par6, double par7)
	{
		super.genTerrainBlocks(par1World, par2Random, par3BlockArray, par4ByteArray, par5, par6, par7);
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