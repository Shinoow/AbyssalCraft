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
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemEthaxiumSlab extends ItemSlab {

	public ItemEthaxiumSlab(Block block) {
		super(block, (BlockSlab)AbyssalCraft.ethaxiumslab1, (BlockSlab)AbyssalCraft.ethaxiumslab2);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.AQUA + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}
}
