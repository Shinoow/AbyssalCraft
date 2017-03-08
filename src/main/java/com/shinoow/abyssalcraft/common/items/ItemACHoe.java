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
package com.shinoow.abyssalcraft.common.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.lib.ACTabs;

public class ItemACHoe extends ItemHoe {

	private EnumChatFormatting format;

	public ItemACHoe(ToolMaterial mat, String name){
		this(mat, name, null);
	}

	public ItemACHoe(ToolMaterial mat, String name, EnumChatFormatting format) {
		super(mat);
		setCreativeTab(ACTabs.tabTools);
		setUnlocalizedName(name);
		this.format = format;
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return format != null ? format + super.getItemStackDisplayName(par1ItemStack) : super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	@SuppressWarnings("incomplete-switch")
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!playerIn.canPlayerEdit(pos.offset(side), side, stack))
			return false;
		else
		{
			int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(stack, playerIn, worldIn, pos);
			if (hook != 0) return hook > 0;

			IBlockState iblockstate = worldIn.getBlockState(pos);
			Block block = iblockstate.getBlock();

			if (side != EnumFacing.DOWN && worldIn.isAirBlock(pos.up()))
			{
				if (block.getMaterial() == Material.grass)
					return useHoe(stack, playerIn, worldIn, pos, Blocks.farmland.getDefaultState());

				if (block == Blocks.dirt)
					switch (iblockstate.getValue(BlockDirt.VARIANT))
					{
					case DIRT:
						return useHoe(stack, playerIn, worldIn, pos, Blocks.farmland.getDefaultState());
					case COARSE_DIRT:
						return useHoe(stack, playerIn, worldIn, pos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
					}
			}

			return false;
		}
	}
}
