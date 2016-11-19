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

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class BlockCoraliumstone extends Block {

	public BlockCoraliumstone() {
		super(Material.ROCK);
		setCreativeTab(ACTabs.tabBlock);
		setTickRandomly(true);
		setSoundType(SoundType.STONE);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random random, int j)
	{
		return Item.getItemFromBlock(ACBlocks.coralium_cobblestone);
	}

	@Override
	public void updateTick(World par1World, BlockPos pos, IBlockState state, Random par5Random) {
		if (!par1World.isRemote)
			for(EnumFacing face : EnumFacing.values())
				if (par1World.getBlockState(pos.offset(face)).getBlock() == ACBlocks.liquid_coralium && par5Random.nextFloat() < 0.3)
					par1World.setBlockState(pos.offset(face), ACBlocks.coralium_stone.getDefaultState());
	}
}