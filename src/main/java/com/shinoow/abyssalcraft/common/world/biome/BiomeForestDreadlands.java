/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
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

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.EntityDreadSpawn;
import com.shinoow.abyssalcraft.common.entity.demon.*;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenDrT;
import com.shinoow.abyssalcraft.lib.ACClientVars;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeForestDreadlands extends BiomeDreadlandsBase
{

	private WorldGenTrees WorldGenDreadTrees;

	public BiomeForestDreadlands(BiomeProperties par1) {
		super(par1);
		topBlock = ACBlocks.dreadlands_grass.getDefaultState();
		fillerBlock = ACBlocks.dreadlands_dirt.getDefaultState();
		WorldGenDreadTrees = new WorldGenDrT(false);
		decorator.treesPerChunk = 6;
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
	public WorldGenAbstractTree getRandomTreeFeature(Random par1Random)
	{
		return WorldGenDreadTrees;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float par1)
	{
		return ACClientVars.getDreadlandsForestSkyColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getGrassColorAtPos(BlockPos pos)
	{
		return ACClientVars.getDreadlandsForestGrassColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return ACClientVars.getDreadlandsForestFoliageColor();
	}
}
