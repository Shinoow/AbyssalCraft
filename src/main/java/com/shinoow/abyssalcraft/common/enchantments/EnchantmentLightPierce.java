/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
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

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;

import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;

public class EnchantmentLightPierce extends EnchantmentDamage {

	public EnchantmentLightPierce(int par1)
	{
		super(par1, 5, 3);
	}

	@Override
	public int getMinEnchantability(int par1)
	{
		return 5 + (par1 - 1) * 8;
	}

	@Override
	public int getMaxEnchantability(int par1)
	{
		return getMinEnchantability(par1) + 20;
	}

	@Override
	public int getMaxLevel()
	{
		return 5;
	}

	@Override
	public float calcModifierLiving(int par1, EntityLivingBase par2EntityLivingBase)
	{
		return par2EntityLivingBase.getCreatureAttribute() == AbyssalCraftAPI.SHADOW ? par1 * 2.5F : 0.0F;
	}

	@Override
	public String getName()
	{
		return "enchantment.damage.shadow";
	}

	@Override
	public boolean canApplyTogether(Enchantment par1Enchantment)
	{
		return !(par1Enchantment instanceof EnchantmentDamage);
	}

	@Override
	public boolean canApply(ItemStack par1ItemStack)
	{
		return par1ItemStack.getItem() instanceof ItemAxe ? true : super.canApply(par1ItemStack);
	}
}