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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.lib.ACTabs;

public class BlockACLeaves extends BlockLeaves {

	private Block sapling;

	public BlockACLeaves(Block sapling) {
		setCreativeTab(ACTabs.tabDecoration);
		setDefaultState(blockState.getBaseState().withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
		this.sapling = sapling;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random par2Random, int par3)
	{
		return Item.getItemFromBlock(sapling);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return Blocks.LEAVES.isOpaqueCube(state);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return Blocks.LEAVES.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return Blocks.LEAVES.getBlockLayer();
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this, 1));
		return ret;
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		List<ItemStack> ret = new java.util.ArrayList<ItemStack>();

		Random rand = world instanceof World ? ((World)world).rand : RANDOM;

		int count = quantityDropped(state, fortune, rand);
		for(int i = 0; i < count; i++)
		{
			Item item = getItemDropped(state, rand, fortune);
			if (item != null)
				ret.add(new ItemStack(item, 1, damageDropped(state)));
		}
		return ret;
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return getStateFromMeta(meta).withProperty(CHECK_DECAY, Boolean.valueOf(false)).withProperty(DECAYABLE, Boolean.valueOf(false));
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;

		if (!state.getValue(DECAYABLE).booleanValue())
			i |= 4;

		if (state.getValue(CHECK_DECAY).booleanValue())
			i |= 8;

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {CHECK_DECAY, DECAYABLE});
	}

	@Override
	public EnumType getWoodType(int meta) {

		return null;
	}
}