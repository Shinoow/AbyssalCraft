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
package com.shinoow.abyssalcraft.common.items.armor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemDreadiumArmor extends ItemArmor {
	public ItemDreadiumArmor(ArmorMaterial par2EnumArmorMaterial, int par3, EntityEquipmentSlot par4, String name){
		super(par2EnumArmorMaterial, par3, par4);
		//		GameRegistry.registerItem(this, name);
		setUnlocalizedName(name);
		setCreativeTab(AbyssalCraft.tabCombat);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return TextFormatting.DARK_RED + super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String layer) {
		if(stack.getItem() == AbyssalCraft.dreadiumhelmet || stack.getItem() == AbyssalCraft.dreadiumplate || stack.getItem() == AbyssalCraft.dreadiumboots)
			return "abyssalcraft:textures/armor/dreadium_1.png";

		if(stack.getItem() == AbyssalCraft.dreadiumlegs)
			return "abyssalcraft:textures/armor/dreadium_2.png";
		else return null;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		if (itemstack.getItem() == AbyssalCraft.dreadiumhelmet) {
			player.addPotionEffect(new PotionEffect(MobEffects.nightVision, 260, 0));
			if(player.getActivePotionEffect(AbyssalCraft.Dplague) !=null)
				player.removePotionEffect(AbyssalCraft.Dplague);
		}
		if (itemstack.getItem() == AbyssalCraft.dreadiumplate)
			player.addPotionEffect(new PotionEffect(MobEffects.resistance, 20, 0));
		if (itemstack.getItem() == AbyssalCraft.dreadiumboots)
			player.addPotionEffect(new PotionEffect(MobEffects.moveSpeed, 20, 1));
	}
}
