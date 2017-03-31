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
package com.shinoow.abyssalcraft.common.blocks;

import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import com.shinoow.abyssalcraft.lib.ACTabs;

public class BlockACFence extends BlockFence {

	public BlockACFence(Material par3Material, String par4, int par5, SoundType stepSound, MapColor mapColor) {
		super(par3Material, mapColor);
		setCreativeTab(ACTabs.tabDecoration);
		this.setHarvestLevel(par4, par5);
		setSoundType(stepSound);
	}

	public BlockACFence(Material par3Material, SoundType stepSound, MapColor mapColor) {
		super(par3Material, mapColor);
		setCreativeTab(ACTabs.tabDecoration);
		setSoundType(stepSound);
		if(getHarvestTool(getDefaultState()) == null)
			if(par3Material == Material.ROCK || par3Material == Material.IRON || par3Material == Material.ANVIL)
				setHarvestLevel("pickaxe", 0);
			else if(par3Material == Material.GROUND || par3Material == Material.GRASS || par3Material == Material.SAND ||
					par3Material == Material.SNOW || par3Material == Material.CRAFTED_SNOW)
				setHarvestLevel("shovel", 0);
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return true;
	}
}
