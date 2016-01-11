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
package com.shinoow.abyssalcraft.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemACBasic extends Item {

	public ItemACBasic(String par1) {
		super();
		setUnlocalizedName(par1);
		setTextureName("abyssalcraft:" + par1);
		setCreativeTab(AbyssalCraft.tabItems);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		if(this.getUnlocalizedName().contains("DSOA") || this.getUnlocalizedName().contains("DAC") ||
				this.getUnlocalizedName().contains("DI") || this.getUnlocalizedName().contains("DF"))
			return EnumChatFormatting.DARK_RED + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
		else if(this.getUnlocalizedName().contains("AI"))
			return EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
		else if(this.getUnlocalizedName().contains("CP") || this.getUnlocalizedName().contains("RCI")
				|| this.getUnlocalizedName().contains("EI"))
			return EnumChatFormatting.AQUA + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");

		return super.getItemStackDisplayName(par1ItemStack);
	}
}
