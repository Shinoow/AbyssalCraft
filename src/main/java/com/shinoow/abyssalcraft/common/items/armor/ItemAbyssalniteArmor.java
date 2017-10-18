/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
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

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class ItemAbyssalniteArmor extends ItemArmor {
	public ItemAbyssalniteArmor(ArmorMaterial par2EnumArmorMaterial, int par3, EntityEquipmentSlot par4, String name){
		super(par2EnumArmorMaterial, par3, par4);
		setUnlocalizedName(name);
		setCreativeTab(ACTabs.tabCombat);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return TextFormatting.DARK_AQUA + super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
	{
		if(stack.getItem() == ACItems.abyssalnite_helmet || stack.getItem() == ACItems.abyssalnite_chestplate || stack.getItem() == ACItems.abyssalnite_boots)
			return "abyssalcraft:textures/armor/abyssalnite_1.png";

		if(stack.getItem() == ACItems.abyssalnite_leggings)
			return "abyssalcraft:textures/armor/abyssalnite_2.png";
		else return null;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		if(world.isRemote || !ACConfig.armorPotionEffects) return;
		if (itemstack.getItem() == ACItems.abyssalnite_helmet)
			player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 20, 0));
		if (itemstack.getItem() == ACItems.abyssalnite_boots)
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20, 0));
	}
}
