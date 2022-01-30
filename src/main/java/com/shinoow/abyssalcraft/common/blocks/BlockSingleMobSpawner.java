/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
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

import javax.annotation.Nullable;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.blocks.tile.*;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSingleMobSpawner extends Block {

	public BlockSingleMobSpawner() {
		super(Material.ROCK);
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getItem(World par1World, BlockPos pos, IBlockState state)
	{
		return ItemStack.EMPTY;
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return NULL_AABB;
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
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		if(state.getBlock() == ACBlocks.chagaroth_spawner)
			return new TileEntityChagarothSpawner();
		else if(state.getBlock() == ACBlocks.dreadguard_spawner)
			return new TileEntityDreadguardSpawner();
		else if(state.getBlock() == ACBlocks.minion_of_the_gatekeeper_spawner)
			return new TileEntityGatekeeperMinionSpawner();
		else if(state.getBlock() == ACBlocks.jzahar_spawner)
			return new TileEntityJzaharSpawner();
		return null;
	}
}
