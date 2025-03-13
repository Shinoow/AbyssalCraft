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

import java.util.Random;

import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.common.entity.ghoul.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenDLT;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenNoTree;
import com.shinoow.abyssalcraft.lib.ACClientVars;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeDarklands extends BiomeDarklandsBase {

	private WorldGenTrees WorldGenDarkTrees;

	public BiomeDarklands(BiomeProperties par1)
	{
		super(par1);
		WorldGenDarkTrees = new WorldGenDLT(false);
		decorator.treesPerChunk = 3;
		decorator.flowersPerChunk= -1;
		decorator.grassPerChunk = -1;
		decorator.mushroomsPerChunk = -1;
		spawnableMonsterList.add(new SpawnListEntry(EntityDepthsGhoul.class, 1, 1, 3));
		spawnableMonsterList.add(new SpawnListEntry(EntityAbyssalZombie.class, 60, 1, 3));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowCreature.class, 70, 3, 3));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowMonster.class, 50, 2, 2));
		spawnableMonsterList.add(new SpawnListEntry(EntityShadowBeast.class, 20, 1, 1));
	}

	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random par1Random)
	{
		return par1Random.nextInt(3) == 0 ? new WorldGenNoTree() : par1Random.nextInt(5) == 0 ? WorldGenDarkTrees : new WorldGenNoTree();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float par1)
	{
		return ACClientVars.getDarklandsSkyColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getGrassColorAtPos(BlockPos pos)
	{
		return ACClientVars.getDarklandsGrassColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return ACClientVars.getDarklandsFoliageColor();
	}
}
