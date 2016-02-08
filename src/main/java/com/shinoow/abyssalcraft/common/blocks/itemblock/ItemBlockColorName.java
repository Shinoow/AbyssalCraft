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
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

/**Shin = lazy. Deal with it.*/
public class ItemBlockColorName extends ItemBlock {

	public ItemBlockColorName(Block block) {
		super(block);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		if(getUnlocalizedName().contains("abyblock"))
			return EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal(getUnlocalizedName() + ".name");
		else if(getUnlocalizedName().contains("odb") || getUnlocalizedName().contains("dreadiumblock") ||
				getUnlocalizedName().contains("darkethaxium"))
			return EnumChatFormatting.DARK_RED + StatCollector.translateToLocal(getUnlocalizedName() + ".name");
		else if(getUnlocalizedName().contains("corblock") || getUnlocalizedName().contains("Eth")
				|| getUnlocalizedName().contains("ethaxium") || getUnlocalizedName().contains("BOE"))
			return EnumChatFormatting.AQUA + StatCollector.translateToLocal(getUnlocalizedName() + ".name");
		else if(getUnlocalizedName().contains("aby"))
			return EnumChatFormatting.BLUE + StatCollector.translateToLocal(getUnlocalizedName() + ".name");

		return StatCollector.translateToLocal(getUnlocalizedName() + ".name");
	}
}
