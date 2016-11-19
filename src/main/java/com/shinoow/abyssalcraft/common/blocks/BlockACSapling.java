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
package com.shinoow.abyssalcraft.common.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockSapling;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.shinoow.abyssalcraft.lib.ACTabs;

public class BlockACSapling extends BlockSapling {

	private WorldGenerator tree;

	public BlockACSapling(WorldGenerator tree) {
		setSoundType(SoundType.PLANT);
		setCreativeTab(ACTabs.tabDecoration);
		this.tree = tree;
	}

	public void growTree(World world, BlockPos pos, IBlockState state, Random random)
	{
		if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, random, pos)) return;

		world.setBlockState(pos, Blocks.AIR.getDefaultState(), 1);
		if(!tree.generate(world, random, pos))
			world.setBlockState(pos, getDefaultState(), 4);
	}

	@Override
	public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand) {

		if (state.getValue(STAGE).intValue() == 0)
			worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
		else
			growTree(worldIn, pos, state, rand);
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		list.add(new ItemStack(itemIn, 1, 0));
	}
}