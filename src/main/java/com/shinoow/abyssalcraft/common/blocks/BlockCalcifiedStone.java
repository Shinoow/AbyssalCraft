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

import java.util.Random;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.blocks.baseblocks.BlockACBasic;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BlockCalcifiedStone extends BlockACBasic {

	public BlockCalcifiedStone() {
		super(Material.ROCK, "pickaxe", 3, 12F, 30F, SoundType.STONE, MapColor.GRAY);
		setTranslationKey("calcifiedstone");
	}

	@Override
	public Item getItemDropped(IBlockState state, Random par2Random, int par3)
	{
		return par2Random.nextBoolean() ? ACItems.crystal_calcium : ACItems.crystal_shard_calcium;
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 1 + par1Random.nextInt(3);
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(this);
	}
}
