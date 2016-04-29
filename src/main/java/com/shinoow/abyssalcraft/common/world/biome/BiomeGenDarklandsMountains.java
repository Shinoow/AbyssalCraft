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

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.entity.EntityShadowBeast;
import com.shinoow.abyssalcraft.common.entity.EntityShadowCreature;
import com.shinoow.abyssalcraft.common.entity.EntityShadowMonster;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiomeGenDarklandsMountains extends BiomeGenBase implements IDarklandsBiome {

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

		if(AbyssalCraft.generateAbyssalniteOre)
			for (int rarity = 0; rarity < var5; ++rarity)
			{
				int veinSize = 1 + par2Random.nextInt(3);
				int x = par3 + par2Random.nextInt(16);
				int y = par2Random.nextInt(28) + 4;
				int z = par4 + par2Random.nextInt(16);

				new WorldGenMinable(AbyssalCraft.abyore, veinSize).generate(par1World, par2Random, x, y, z);
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
