/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.world.biome;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.common.entity.ghoul.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.lib.ACClientVars;

import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeDarklandsMountains extends BiomeDarklandsBase {

	public BiomeDarklandsMountains(BiomeProperties par1)
	{
		super(par1);
		topBlock = ACBlocks.darkstone.getDefaultState();
		fillerBlock = ACBlocks.darkstone.getDefaultState();
		staticTopBlock = staticFillerBlock = true;
		decorator.treesPerChunk = 0;
		spawnableMonsterList.add(new SpawnListEntry(EntityDepthsGhoul.class, 1, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityAbyssalZombie.class, 45, 1, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowCreature.class, 100, 3, 3));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowMonster.class, 80, 2, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowBeast.class, 40, 1, 1));
		aw.clear();
		aw.add(new SpawnListEntry(EntityZombie.class, 50, 1, 5));
		aw.add(new SpawnListEntry(EntitySkeleton.class, 50, 1, 5));
		aw.add(new SpawnListEntry(EntityDepthsGhoul.class, 1, 1, 3));
		aw.add(new SpawnListEntry(EntityAbyssalZombie.class, 45, 1, 5));
		aw.add(new SpawnListEntry(EntitySkeletonGoliath.class, 15, 1, 1));
		aw.add(new SpawnListEntry(EntityShadowCreature.class, 100, 3, 3));
		aw.add(new SpawnListEntry(EntityShadowMonster.class, 80, 2, 2));
		aw.add(new SpawnListEntry(EntityShadowBeast.class, 40, 1, 1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float par1)
	{
		return ACClientVars.getDarklandsMountainsSkyColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getGrassColorAtPos(BlockPos pos)
	{
		return ACClientVars.getDarklandsMountainsGrassColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return ACClientVars.getDarklandsMountainsFoliageColor();
	}
}
