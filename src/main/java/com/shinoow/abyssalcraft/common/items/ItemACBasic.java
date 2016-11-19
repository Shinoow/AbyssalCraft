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
import com.shinoow.abyssalcraft.lib.ACTabs;

public class ItemACBasic extends Item {

	public ItemACBasic(String par1) {
		setUnlocalizedName(par1);
		setCreativeTab(ACTabs.tabItems);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		if(this.getUnlocalizedName().contains("dreadshard") || this.getUnlocalizedName().contains("dreadchunk") ||
				this.getUnlocalizedName().contains("dreadiumingot") || this.getUnlocalizedName().contains("dreadfragment"))
			return EnumChatFormatting.DARK_RED + super.getItemStackDisplayName(par1ItemStack);
		else if(this.getUnlocalizedName().contains("abyingot"))
			return EnumChatFormatting.DARK_AQUA + super.getItemStackDisplayName(par1ItemStack);
		else if(this.getUnlocalizedName().contains("cpearl") || this.getUnlocalizedName().contains("cingot")
				|| this.getUnlocalizedName().contains("ethaxiumingot"))
			return EnumChatFormatting.AQUA + super.getItemStackDisplayName(par1ItemStack);

		return super.getItemStackDisplayName(par1ItemStack);
	}
}