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
package com.shinoow.abyssalcraft.common.blocks.itemblock;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.blocks.BlockShoggothOoze;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemShoggothOoze extends ItemBlockAC
{
	public ItemShoggothOoze(Block block)
	{
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack stack = player.getHeldItem(hand);

		if (!stack.isEmpty() && player.canPlayerEdit(pos, facing, stack))
		{
			IBlockState iblockstate = worldIn.getBlockState(pos);
			Block block = iblockstate.getBlock();
			BlockPos blockpos = pos;

			if ((facing != EnumFacing.UP || block != this.block) && !block.isReplaceable(worldIn, pos))
			{
				blockpos = pos.offset(facing);
				iblockstate = worldIn.getBlockState(blockpos);
				block = iblockstate.getBlock();
			}

			if (block == this.block)
			{
				int i = iblockstate.getValue(BlockShoggothOoze.LAYERS);

				if (i <= 7)
				{
					IBlockState iblockstate1 = iblockstate.withProperty(BlockShoggothOoze.LAYERS, i + 1);
					AxisAlignedBB axisalignedbb = iblockstate1.getCollisionBoundingBox(worldIn, blockpos);

					if (axisalignedbb != Block.NULL_AABB && worldIn.checkNoEntityCollision(axisalignedbb.offset(blockpos)) && worldIn.setBlockState(blockpos, iblockstate1, 10))
					{
						SoundType soundtype = this.block.getSoundType(iblockstate1, worldIn, blockpos, player);
						worldIn.playSound(player, blockpos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
						stack.shrink(1);
						return EnumActionResult.SUCCESS;
					}
				}
			}

			return super.onItemUse(player, worldIn, blockpos, hand, facing, hitX, hitY, hitZ);
		} else
			return EnumActionResult.FAIL;
	}

	/**
	 * Converts the given ItemStack damage value into a metadata value to be placed in the world when this Item is
	 * placed as a Block (mostly used with ItemBlocks).
	 */
	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack)
	{
		IBlockState state = world.getBlockState(pos);
		return state.getBlock() != ACBlocks.shoggoth_ooze || state.getValue(BlockShoggothOoze.LAYERS) > 7 ? super.canPlaceBlockOnSide(world, pos, side, player, stack) : true;
	}
}
