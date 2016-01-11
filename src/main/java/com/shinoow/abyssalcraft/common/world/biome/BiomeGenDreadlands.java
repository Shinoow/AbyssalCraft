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

import com.shinoow.abyssalcraft.common.entity.EntityDreadgolem;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenDreadlandsStalagmite;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;

public class BiomeGenDreadlands extends BiomeGenDreadlandsBase
{

	@SuppressWarnings("unchecked")
	public BiomeGenDreadlands(int par1) {
		super(par1);
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadgolem.class, 100, 1, 5));
	}

	@Override
	public void decorate(World world, Random rand, int x, int z)
	{
		super.decorate(world, rand, x, z);

		for(int i = 0; i < 1; i++){
			int xPos = x + rand.nextInt(16) + 8;
			int zPos = z + rand.nextInt(16) + 8;
			new WorldGenDreadlandsStalagmite().generate(world, rand, xPos, world.getHeightValue(xPos, zPos), zPos);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeGrassColor(int par1, int par2, int par3)
	{
		return 0x910000;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeFoliageColor(int par1, int par2, int par3)
	{
		return 0x910000;
	}
}
