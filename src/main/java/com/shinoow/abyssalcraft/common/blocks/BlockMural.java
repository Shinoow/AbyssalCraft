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
package com.shinoow.abyssalcraft.common.blocks;

import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMural extends BlockACBasic {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockMural() {
		super(Material.ROCK, 5.0F, 10.0F, SoundType.STONE, MapColor.BLACK);
		setCreativeTab(ACTabs.tabDecoration);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setTranslationKey("mural");
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		EnumFacing facing = state.getValue(FACING);

		switch(facing) {
		case EAST:
			return new AxisAlignedBB(0.0F, 0.0F, 0.0F, 0.2F, 1.0F, 1.0F);
		case NORTH:
			return new AxisAlignedBB(0.0F, 0.0F, 0.8F, 1.0F, 1.0F, 1.0F);
		case SOUTH:
			return new AxisAlignedBB(0.0F, 0.0F, 0.2F, 1.0F, 1.0F, 0.0F);
		case WEST:
			return new AxisAlignedBB(1.0F, 0.0F, 0.0F, 0.8F, 1.0F, 1.0F);
		default:
			return new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.2F);
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getIndex();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer.Builder(this).add(FACING).build();
	}
}
