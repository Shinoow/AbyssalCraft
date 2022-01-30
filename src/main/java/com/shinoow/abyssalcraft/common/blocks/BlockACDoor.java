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

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockACDoor extends BlockDoor {

	private MapColor mapColor;

	public BlockACDoor(Material material, float hardness, float resistance, SoundType stepsound, MapColor mapColor) {
		super(material);
		setHardness(hardness);
		setResistance(resistance);
		setSoundType(stepsound);
		setHarvestLevel("axe", 0);
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_)
	{
		return mapColor;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? Items.AIR : getItem();
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(getItem());
	}

	private Item getItem() {
		if(this == ACBlocks.darklands_oak_door)
			return ACItems.darklands_oak_door;
		if(this == ACBlocks.dreadlands_door)
			return ACItems.dreadlands_door;
		return null;
	}
}
