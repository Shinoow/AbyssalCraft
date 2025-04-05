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
package com.shinoow.abyssalcraft.common.items.armor;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemDreadArmor extends ItemACArmor {
	public ItemDreadArmor(ArmorMaterial par2EnumArmorMaterial, int par3, EntityEquipmentSlot par4, String name){
		super(par2EnumArmorMaterial, par3, par4, name);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return TextFormatting.DARK_RED + super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String layer) {
		if(stack.getItem() == ACItems.dreaded_abyssalnite_helmet || stack.getItem() == ACItems.dreaded_abyssalnite_chestplate || stack.getItem() == ACItems.dreaded_abyssalnite_boots)
			return "abyssalcraft:textures/armor/dread_1.png";

		if(stack.getItem() == ACItems.dreaded_abyssalnite_leggings)
			return "abyssalcraft:textures/armor/dread_2.png";
		else return null;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		if(world.isRemote || !ACConfig.armorPotionEffects) return;
		if (itemstack.getItem() == ACItems.dreaded_abyssalnite_helmet){
			if(world.provider.isSurfaceWorld())
				player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 260, 0, false, false));
			if(player.isPotionActive(AbyssalCraftAPI.dread_plague))
				player.removePotionEffect(AbyssalCraftAPI.dread_plague);
		}
		if (itemstack.getItem() == ACItems.dreaded_abyssalnite_chestplate)
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 20, 3, false, false));
		if (itemstack.getItem() == ACItems.dreaded_abyssalnite_leggings)
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 20, 3, false, false));
		if (itemstack.getItem() == ACItems.dreaded_abyssalnite_boots)
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 20, 3, false, false));
	}
}
