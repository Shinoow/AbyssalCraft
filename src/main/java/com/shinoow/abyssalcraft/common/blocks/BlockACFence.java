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
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

import com.shinoow.abyssalcraft.lib.ACTabs;

public class BlockACFence extends BlockFence {

	public BlockACFence(Material par3Material, String par4, int par5, MapColor mapColor) {
		super(par3Material, mapColor);
		setCreativeTab(ACTabs.tabDecoration);
		this.setHarvestLevel(par4, par5);
	}

	public BlockACFence(Material par3Material, MapColor mapColor) {
		super(par3Material, mapColor);
		setCreativeTab(ACTabs.tabDecoration);
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockAccess world, BlockPos pos) {
		return true;
	}
}
