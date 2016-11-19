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

import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemWashCloth extends Item {

	public ItemWashCloth() {
		super();
		setUnlocalizedName("cloth");
		setCreativeTab(ACTabs.tabItems);
		setMaxDamage(20);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		ItemStack result = stack.copy();
		result.setItemDamage(stack.getItemDamage() + 1);
		return result;
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return stack.getItemDamage() < stack.getMaxDamage();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		l.add("This item has been used " + getDamage(is) + " out of 20 times");
	}
}