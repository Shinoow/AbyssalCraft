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

import java.util.List;

import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockACPressureplate extends BlockBasePressurePlate
{
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	private BlockACPressureplate.Sensitivity sensitivity;


	public BlockACPressureplate(String par2Str, Material par3Material, BlockACPressureplate.Sensitivity par4EnumMobType, String par5, int par6, SoundType stepSound)
	{
		super(par3Material);
		sensitivity = par4EnumMobType;
		this.setHarvestLevel(par5, par6);
		setDefaultState(blockState.getBaseState().withProperty(POWERED, false));
		setSoundType(stepSound);
	}

	public BlockACPressureplate(String par2Str, Material par3Material, BlockACPressureplate.Sensitivity par4EnumMobType, SoundType stepSound)
	{
		super(par3Material);
		sensitivity = par4EnumMobType;
		setDefaultState(blockState.getBaseState().withProperty(POWERED, false));
		setSoundType(stepSound);
	}

	@Override
	protected int getRedstoneStrength(IBlockState state)
	{
		return state.getValue(POWERED) ? 15 : 0;
	}

	@Override
	protected IBlockState setRedstoneStrength(IBlockState state, int strength)
	{
		return state.withProperty(POWERED, strength > 0);
	}

	/**
	 * Returns the current state of the pressure plate. Returns a value between 0 and 15 based on the number of items on
	 * it.
	 */
	@Override
	protected int computeRedstoneStrength(World worldIn, BlockPos pos)
	{
		AxisAlignedBB axisalignedbb = PRESSURE_AABB.offset(pos);
		List <? extends Entity > list;

		switch (sensitivity)
		{
		case EVERYTHING:
			list = worldIn.getEntitiesWithinAABBExcludingEntity((Entity)null, axisalignedbb);
			break;
		case MOBS:
			list = worldIn.<Entity>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
			break;
		default:
			return 0;
		}

		if (!list.isEmpty())
			for (Entity entity : list)
				if (!entity.doesEntityNotTriggerPressurePlate())
					return 15;

		return 0;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(POWERED, meta == 1);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(POWERED) ? 1 : 0;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {POWERED});
	}

	public enum Sensitivity
	{
		EVERYTHING,
		MOBS;
	}

	@Override
	protected void playClickOnSound(World worldIn, BlockPos color)
	{
		if (material == Material.WOOD)
			worldIn.playSound((EntityPlayer)null, color, SoundEvents.BLOCK_WOOD_PRESSPLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.8F);
		else
			worldIn.playSound((EntityPlayer)null, color, SoundEvents.BLOCK_STONE_PRESSPLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
	}

	@Override
	protected void playClickOffSound(World worldIn, BlockPos pos)
	{
		if (material == Material.WOOD)
			worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_WOOD_PRESSPLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.7F);
		else
			worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_STONE_PRESSPLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.5F);
	}
}
