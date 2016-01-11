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

public class EnchantmentIronWall extends Enchantment {

	public EnchantmentIronWall(int par1, int par2) {
		super(par1, par2, EnumEnchantmentType.armor_torso);
		setName("ironWall");
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
	public int getMaxLevel(){
		return 1;
	}
}
