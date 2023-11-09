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
package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWashCloth extends ItemACBasic {

	public ItemWashCloth() {
		super("washcloth");
		setMaxDamage(20);
		setMaxStackSize(1);
		setNoRepair();
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

	@Override
	public void addInformation(ItemStack is, World player, List l, ITooltipFlag B){
		l.add("This item has been used " + getDamage(is) + " out of 20 times");
	}
}
