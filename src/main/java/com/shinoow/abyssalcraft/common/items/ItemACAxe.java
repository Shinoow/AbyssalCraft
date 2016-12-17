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

import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class ItemACAxe extends ItemAxe {

	private TextFormatting format;

	public ItemACAxe(ToolMaterial mat, String name, int harvestlevel){
		this(mat, name, harvestlevel, null);
	}

	public ItemACAxe(ToolMaterial mat, String name, int harvestlevel, TextFormatting format) {
		super(mat, mat == AbyssalCraftAPI.darkstoneTool ? 8 : mat.getDamageVsEntity() + 5, -3.0F);
		setCreativeTab(ACTabs.tabTools);
		setHarvestLevel("axe", harvestlevel);
		setUnlocalizedName(name);
		this.format = format;
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return format != null ? format + super.getItemStackDisplayName(par1ItemStack) : super.getItemStackDisplayName(par1ItemStack);
	}
}