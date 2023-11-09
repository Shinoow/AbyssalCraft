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

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOmotholFire extends Block {

	public BlockOmotholFire()
	{
		super(Material.FIRE);
		setTickRandomly(true);
	}

	@Override
	public boolean isBurning(IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block neighborBlock, BlockPos pos2)
	{
		if(!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP))
			worldIn.setBlockToAir(pos);
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
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

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

	@Override
	public int tickRate(World worldIn)
	{
		return 30;
	}

	@Override
	public boolean requiresUpdates()
	{
		return false;
	}

	@Override
	public boolean isCollidable()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public void onEntityCollision(World par1World, BlockPos pos, IBlockState state, Entity par5Entity) {
		super.onEntityCollision(par1World, pos, state, par5Entity);

		if(par5Entity instanceof EntityLivingBase)
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100));
	}

	@Override
	public void onBlockAdded(World par1World, BlockPos pos, IBlockState state)
	{

		if (!BlockOmotholPortal.tryToCreatePortal(par1World, pos))
			if (!par1World.getBlockState(pos.down()).isSideSolid(par1World, pos.down(), EnumFacing.UP))
				par1World.setBlockToAir(pos);
			else
				par1World.scheduleUpdate(pos, this, tickRate(par1World) + par1World.rand.nextInt(10));
	}
}
