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

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class BlockDarkstone extends Block {

	public BlockDarkstone(){
		super(Material.rock);
		setCreativeTab(AbyssalCraft.tabBlock);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random random, int j)
	{
		return Item.getItemFromBlock(AbyssalCraft.Darkstone_cobble);
	}
}
