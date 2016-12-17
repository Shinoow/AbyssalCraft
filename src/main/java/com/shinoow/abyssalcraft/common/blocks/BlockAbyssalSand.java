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
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.block.ACBlocks;

public class BlockAbyssalSand extends BlockACBasic {

	public BlockAbyssalSand() {
		super(Material.sand, 0.5F, 2.5F, Block.soundTypeSand);
		setUnlocalizedName("abyssalsand");
		setTickRandomly(true);
	}

	@Override
	public void updateTick(World par1World, BlockPos pos, IBlockState state, Random par5Random) {
		if (!par1World.isRemote && par5Random.nextInt(10) == 0 && par1World.getLightFromNeighbors(pos.up()) >= 13
				&& !par1World.isSideSolid(pos.up(), EnumFacing.DOWN) && !par1World.getBlockState(pos.up()).getBlock().getMaterial().isLiquid())
			par1World.setBlockState(pos, ACBlocks.fused_abyssal_sand.getDefaultState());
	}

	@Override
	public int tickRate(World worldIn)
	{
		return 40;
	}
}
