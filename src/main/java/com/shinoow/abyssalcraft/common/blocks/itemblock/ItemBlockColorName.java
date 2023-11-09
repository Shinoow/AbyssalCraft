/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

/**Shin = lazy. Deal with it.*/
public class ItemBlockColorName extends ItemBlockAC {

	public ItemBlockColorName(Block block) {
		super(block);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		if(getTranslationKey().contains("odb") || getTranslationKey().contains("darkethaxium"))
			return TextFormatting.DARK_RED + super.getItemStackDisplayName(par1ItemStack);
		else if(getTranslationKey().contains("ethaxium"))
			return TextFormatting.AQUA + super.getItemStackDisplayName(par1ItemStack);
		else if(getTranslationKey().contains("aby"))
			return TextFormatting.BLUE + super.getItemStackDisplayName(par1ItemStack);
		return super.getItemStackDisplayName(par1ItemStack);
	}
}
