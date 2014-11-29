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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.*;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiomeGenDarklandsMountains extends BiomeGenBase {

	@SuppressWarnings("unchecked")
	public BiomeGenDarklandsMountains(int par1)
	{
		super(par1);
		rootHeight = 1.3F;
		heightVariation = 0.9F;
		topBlock = AbyssalCraft.Darkstone;
		fillerBlock = AbyssalCraft.Darkstone;
		waterColorMultiplier = 14745518;
		theBiomeDecorator.treesPerChunk = 0;
		spawnableMonsterList.add(new SpawnListEntry(EntityDepthsGhoul.class, 45, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityAbyssalZombie.class, 45, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowCreature.class, 60, 1, 3));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowMonster.class, 45, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowBeast.class, 15, 1, 1));
	}
	@Override
	public void decorate(World par1World, Random par2Random, int par3, int par4)
	{
		super.decorate(par1World, par2Random, par3, par4);
		int var5 = 3 + par2Random.nextInt(6);

		for (int rarity = 0; rarity < var5; ++rarity)
		{
			int x = par3 + par2Random.nextInt(16);
			int y = par2Random.nextInt(28) + 4;
			int z = par4 + par2Random.nextInt(16);
			Block var10 = par1World.getBlock(x, y, z);

			if (var10.isReplaceableOreGen(par1World, x, y, z, Blocks.stone))
				par1World.setBlock(x, y, z, AbyssalCraft.abyore);
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
		return 0x30217A;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeFoliageColor(int par1, int par2, int par3)
	{
		return 0x30217A;
	}
}