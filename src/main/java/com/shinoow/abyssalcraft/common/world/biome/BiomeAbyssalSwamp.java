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

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenDeadTree;
import com.shinoow.abyssalcraft.lib.ACClientVars;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeAbyssalSwamp extends BiomeAbyssalWastelandBase {

	private WorldGenTrees tree;

	public BiomeAbyssalSwamp(BiomeProperties properties) {
		super(properties);
		topBlock = ACBlocks.fused_abyssal_sand.getDefaultState();
		fillerBlock = ACBlocks.abyssal_sand.getDefaultState();
		tree = new WorldGenDeadTree(false).setIsWorldGen();
		decorator.treesPerChunk = 1;
	}

	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random par1Random)
	{
		return tree;
	}

	@Override
	public void decorate(World worldIn, Random rand, BlockPos pos)
	{
		decorator.decorate(worldIn, rand, this, pos);
		super.decorate(worldIn, rand, pos);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float par1)
	{
		return ACClientVars.getAbyssalSwampSkyColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getGrassColorAtPos(BlockPos pos)
	{
		return ACClientVars.getAbyssalSwampGrassColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return ACClientVars.getAbyssalSwampFoliageColor();
	}
}
