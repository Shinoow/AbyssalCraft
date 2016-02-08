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

import com.shinoow.abyssalcraft.AbyssalCraft;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class ItemACSword extends ItemSword {

	private EnumChatFormatting format;

	public ItemACSword(ToolMaterial mat, String name){
		this(mat, name, null);
	}

	public ItemACSword(ToolMaterial mat, String name, EnumChatFormatting format) {
		super(mat);
		setCreativeTab(AbyssalCraft.tabCombat);
		//		GameRegistry.registerItem(this, name);
		setUnlocalizedName(name);
		//		setTextureName(AbyssalCraft.modid + ":" + name);
		this.format = format;
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return format != null ? format + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name") : super.getItemStackDisplayName(par1ItemStack);
	}
}
