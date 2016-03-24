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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

/** Some sort of universal metadata itemblock thingy */
public class ItemMetadataBlock extends ItemBlock {

	private static final String[] subNames = {
		"0", "1",  "2", "3", "4", "5", "6", "7",
		"8", "9", "10", "11", "12", "13", "14", "15"};

	public ItemMetadataBlock(Block b) {
		super(b);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		if(getUnlocalizedName().contains("darkethaxiumbrick"))
			return TextFormatting.DARK_RED + I18n.translateToLocal(getUnlocalizedName() + "." + subNames[par1ItemStack.getItemDamage()] + ".name");
		else if(getUnlocalizedName().contains("ethaxiumbrick"))
			return TextFormatting.AQUA + I18n.translateToLocal(getUnlocalizedName() + "." + subNames[par1ItemStack.getItemDamage()] + ".name");
		return I18n.translateToLocal(getUnlocalizedName() + "." + subNames[par1ItemStack.getItemDamage()] + ".name");
	}
}
