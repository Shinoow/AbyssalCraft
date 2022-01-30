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

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlockFusedAbyssalSand extends BlockACBasic {

	public BlockFusedAbyssalSand() {
		super(Material.SAND, 0.5F, 2.5F, SoundType.SAND, MapColor.LIME);
		setTranslationKey("fusedabyssalsand");
	}

	@Override
	public Item getItemDropped(IBlockState state, Random random, int j)
	{
		return Item.getItemFromBlock(ACBlocks.abyssal_sand);
	}
}
