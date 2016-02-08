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
package com.shinoow.abyssalcraft.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentWeaponInfusion extends Enchantment {

	public EnchantmentWeaponInfusion(int par1, int par2, String par3Str) {
		super(par1, new ResourceLocation("abyssalcraft", par3Str), par2, EnumEnchantmentType.WEAPON);
		setName(par3Str);
	}

	@Override
	public int getMinEnchantability(int par1)
	{
		return 14;
	}

	@Override
	public int getMaxEnchantability(int par1)
	{
		return super.getMinEnchantability(par1) + 30;
	}

	@Override
	public int getMaxLevel()
	{
		return 1;
	}

	@Override
	public boolean canApplyTogether(Enchantment par1Enchantment)
	{
		return !(par1Enchantment instanceof EnchantmentWeaponInfusion);
	}
}
