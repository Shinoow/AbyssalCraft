/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
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
import com.shinoow.abyssalcraft.api.entity.EntityUtil;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class EnchantmentWeaponInfusion extends Enchantment {

	public EnchantmentWeaponInfusion(String par3Str) {
		//		super(par1, new ResourceLocation("abyssalcraft", par3Str), par2, EnumEnchantmentType.WEAPON);
		super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		setName("abyssalcraft."+par3Str);
	}

	@Override
	public int getMinEnchantability(int par1)
	{
		return Short.MAX_VALUE;
	}

	@Override
	public int getMaxEnchantability(int par1)
	{
		return getMinEnchantability(par1) + 30;
	}

	@Override
	public int getMaxLevel()
	{
		return 1;
	}

	@Override
	public void onEntityDamaged(EntityLivingBase user, Entity target, int level)
	{
		if(!user.world.isRemote)
			if(target instanceof EntityLivingBase){
				EntityLivingBase entity = (EntityLivingBase) target;
				if(user instanceof EntityPlayer && target instanceof EntityPlayer && !((EntityPlayer) user).canAttackPlayer((EntityPlayer)target))
					return;
				if(this == AbyssalCraftAPI.coralium_enchantment)
					if(!EntityUtil.isEntityCoralium(entity))
						entity.addPotionEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 100));
				if(this == AbyssalCraftAPI.dread_enchantment)
					if(!EntityUtil.isEntityDread(entity))
						entity.addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100));
			}
	}

	@Override
	public boolean canApply(ItemStack stack)
	{
		return super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack)
	{
		return false;
	}

	@Override
	public boolean isAllowedOnBooks()
	{
		return false;
	}

	@Override
	public boolean canApplyTogether(Enchantment par1Enchantment)
	{
		return !(par1Enchantment instanceof EnchantmentWeaponInfusion);
	}
}
