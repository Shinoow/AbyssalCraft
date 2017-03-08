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
package com.shinoow.abyssalcraft.common.blocks.itemblock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.init.BlockHandler;

public class ItemDarkEthaxiumSlab extends ItemSlab {

	public ItemDarkEthaxiumSlab(Block block) {
		super(block, (BlockSlab)ACBlocks.dark_ethaxium_brick_slab, (BlockSlab)BlockHandler.darkethaxiumslab2);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return TextFormatting.DARK_RED + super.getItemStackDisplayName(par1ItemStack);
	}
}
