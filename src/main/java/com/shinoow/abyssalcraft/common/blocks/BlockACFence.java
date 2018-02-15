/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

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
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity)
	{
		if(entity instanceof EntityDragon || entity instanceof EntityWither || entity instanceof EntityWitherSkull)
			return state.getBlock() != ACBlocks.ethaxium_brick_fence && state.getBlock() != ACBlocks.dark_ethaxium_brick_fence;
		return super.canEntityDestroy(state, world, pos, entity);
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return true;
	}
}
