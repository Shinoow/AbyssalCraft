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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenShoggothMonolith;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiomeGenShoggothPlains extends BiomeGenBase {

	private WorldGenShoggothMonolith monolith = new WorldGenShoggothMonolith();

	@SuppressWarnings("unchecked")
	public BiomeGenShoggothPlains(int par1)
	{
		super(par1);
		topBlock = AbyssalCraft.shoggothBlock;
		fillerBlock = Blocks.dirt;
		waterColorMultiplier = 14745518;
		spawnableMonsterList.clear();
		spawnableMonsterList.add(new SpawnListEntry(EntityLesserShoggoth.class, 20, 1, 3));
	}

	@Override
	public void decorate(World world, Random rand, int x, int z)
	{
		for(int i = 0; i < 1; i++){
			int posX = x + rand.nextInt(16) + 16;
			int posZ = z + rand.nextInt(16) + 16;
			if(rand.nextInt(10) == 0)
				monolith.generate(world, rand, posX, world.getHeightValue(posX, posZ), posZ);
		}

		super.decorate(world, rand, x, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float par1)
	{
		return 0;
	}
}
