/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.enchantments;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentSapping extends Enchantment {

	public EnchantmentSapping() {
		super(Rarity.UNCOMMON, AbyssalCraftAPI.STAFF_OF_RENDING, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
		setName("abyssalcraft.sapping");
	}

	@Override
	public int getMinEnchantability(int par1)
	{
		return 12 + (par1 - 1) * 8;
	}

	@Override
	public int getMaxEnchantability(int par1)
	{
		return getMinEnchantability(par1) + 20;
	}

	@Override
	public int getMaxLevel()
	{
		return 3;
	}
}
