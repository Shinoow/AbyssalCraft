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

import java.util.List;

import com.shinoow.abyssalcraft.AbyssalCraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class ItemEoA extends Item {

	public ItemEoA() {
		super();
		setMaxStackSize(1);
		//		GameRegistry.registerItem(this, "eoa");
		setUnlocalizedName("eoa");
		setCreativeTab(AbyssalCraft.tabItems);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.AQUA + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		l.add(StatCollector.translateToLocal("tooltip.eoa"));
	}
}
