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
package com.shinoow.abyssalcraft.common.enchantments;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class EnchantmentLightPierce extends EnchantmentDamage {

	public EnchantmentLightPierce()
	{
		//		super(par1, new ResourceLocation("abyssalcraft", "light_pierce"), 5, 3);
		super(Rarity.COMMON, 5, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
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
	public float calcDamageByCreature(int par1, EnumCreatureAttribute par2CreatureAttribute)
	{
		return par2CreatureAttribute == AbyssalCraftAPI.SHADOW ? par1 * 2.5F : 0.0F;
	}

	@Override
	public String getName()
	{
		return "enchantment.abyssalcraft.light_pierce";
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
