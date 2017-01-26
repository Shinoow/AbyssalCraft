/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
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

import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTrees;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.EntityDreadSpawn;
import com.shinoow.abyssalcraft.common.entity.demon.EntityDemonChicken;
import com.shinoow.abyssalcraft.common.entity.demon.EntityDemonCow;
import com.shinoow.abyssalcraft.common.entity.demon.EntityDemonPig;
import com.shinoow.abyssalcraft.common.entity.demon.EntityDemonSheep;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenDrT;

public class BiomeGenForestDreadlands extends BiomeGenDreadlandsBase {

	private WorldGenTrees WorldGenDreadTrees;

	public BiomeGenForestDreadlands(int par1) {
		super(par1);
		topBlock = ACBlocks.dreadlands_grass.getDefaultState();
		fillerBlock = ACBlocks.dreadlands_dirt.getDefaultState();
		WorldGenDreadTrees = new WorldGenDrT(false);
		theBiomeDecorator.treesPerChunk = 6;
	}

	@Override
	public final void setMobSpawns(){
		super.setMobSpawns();
		spawnableMonsterList.add(new SpawnListEntry(EntityDemonPig.class, 40, 1, 3));
		spawnableMonsterList.add(new SpawnListEntry(EntityDemonCow.class, 40, 1, 3));
		spawnableMonsterList.add(new SpawnListEntry(EntityDemonChicken.class, 40, 1, 3));
		spawnableMonsterList.add(new SpawnListEntry(EntityDemonSheep.class, 40, 1, 3));
		spawnableMonsterList.add(new SpawnListEntry(EntityDreadSpawn.class, 50, 1, 2));
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(Random par1Random)
	{
		return WorldGenDreadTrees;
	}
}
