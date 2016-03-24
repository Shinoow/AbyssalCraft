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
package com.shinoow.abyssalcraft.common.blocks.itemblock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemAbySlab extends ItemSlab {

	public ItemAbySlab(Block block) {
		super(block, (BlockSlab)AbyssalCraft.abyslab1, (BlockSlab)AbyssalCraft.abyslab2);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return TextFormatting.BLUE + super.getItemStackDisplayName(par1ItemStack);
	}
}
