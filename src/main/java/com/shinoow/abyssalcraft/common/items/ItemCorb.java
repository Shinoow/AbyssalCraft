/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
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

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemCorb extends ItemACBasic {

	public ItemCorb() {
		super("transmutationgem");
		maxStackSize = 1;
		setMaxDamage(10);
		setNoRepair();
		setCreativeTab(ACTabs.tabTools);
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
	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
	{
		return false;
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return TextFormatting.AQUA + super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public void addInformation(ItemStack is, World player, List<String> l, ITooltipFlag B){
		l.add(I18n.format("tooltip.corb"));
		if(!B.isAdvanced())
			l.add(String.format("%d/%d", getMaxDamage(is) - getDamage(is), getMaxDamage(is)));
	}

	@Override
	public boolean hasEffect(ItemStack is){
		return true;
	}
}
