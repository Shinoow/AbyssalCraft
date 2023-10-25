/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks;

import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockEthaxiumPillar extends BlockRotatedPillar {

	public BlockEthaxiumPillar(float hardness, MapColor mapColor) {
		super(Material.ROCK, mapColor);
		setDefaultState(blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.Y));
		setHarvestLevel("pickaxe", 8);
		setHardness(hardness);
		setResistance(Float.MAX_VALUE);
		setSoundType(SoundType.STONE);
		setCreativeTab(ACTabs.tabBlock);
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity)
	{
		if(entity instanceof EntityDragon || entity instanceof EntityWither || entity instanceof EntityWitherSkull)
			return false;
		return super.canEntityDestroy(state, world, pos, entity);
	}
}
