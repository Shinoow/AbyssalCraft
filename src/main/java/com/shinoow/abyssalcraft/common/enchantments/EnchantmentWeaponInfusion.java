/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentWeaponInfusion extends Enchantment {

	public EnchantmentWeaponInfusion(int par1, int par2, String par3Str) {
		super(par1, par2, EnumEnchantmentType.weapon);
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