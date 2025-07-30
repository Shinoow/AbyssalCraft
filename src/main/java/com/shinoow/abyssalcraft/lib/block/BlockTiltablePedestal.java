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
package com.shinoow.abyssalcraft.lib.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/**
 * Base-block for pedestal blocks with the "tilting" property and logic
 * handled in one place
 * @author shinoow
 *
 */
public abstract class BlockTiltablePedestal extends BlockSingletonInventory {

	public static final PropertyBool TILTED = PropertyBool.create("tilted");

	protected BlockTiltablePedestal(Material materialIn, MapColor color) {
		super(materialIn, color);
		setDefaultState(blockState.getBaseState().withProperty(TILTED, false));
	}

	protected BlockTiltablePedestal(Material materialIn) {
		super(materialIn);
		setDefaultState(blockState.getBaseState().withProperty(TILTED, false));
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		int i = MathHelper.floor(placer.rotationYaw * 16.0F / 360.0F + 0.5D) & 15;

		// mod 4 gives a decent enough "looking at a straight angle"
		// thereby not tilted
		return getDefaultState().withProperty(TILTED, i % 4 != 0);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(TILTED, (meta & 1) > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(TILTED) ? 1 : 0;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer.Builder(this).add(TILTED).build();
	}
}
