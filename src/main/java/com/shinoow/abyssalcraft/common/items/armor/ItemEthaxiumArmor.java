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

public class ItemEthaxiumArmor extends ItemACArmor {
	public ItemEthaxiumArmor(ArmorMaterial par2EnumArmorMaterial, int par3, EntityEquipmentSlot par4, String name){
		super(par2EnumArmorMaterial, par3, par4, name);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return TextFormatting.AQUA + super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String layer) {
		if(stack.getItem() == ACItems.ethaxium_helmet || stack.getItem() == ACItems.ethaxium_chestplate || stack.getItem() == ACItems.ethaxium_boots)
			return "abyssalcraft:textures/armor/ethaxium_1.png";

		if(stack.getItem() == ACItems.ethaxium_leggings)
			return "abyssalcraft:textures/armor/ethaxium_2.png";
		else return null;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		if(world.isRemote || !ACConfig.armorPotionEffects) return;
		if (itemstack.getItem() == ACItems.ethaxium_helmet) {
			if(world.provider.isSurfaceWorld() && !world.provider.isDaytime())
				player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 260, 0, false, false));
			if(player.isPotionActive(MobEffects.HUNGER))
				player.removePotionEffect(MobEffects.HUNGER);
			if(player.isPotionActive(MobEffects.POISON))
				player.removePotionEffect(MobEffects.POISON);
			if(player.isPotionActive(AbyssalCraftAPI.coralium_plague))
				player.removePotionEffect(AbyssalCraftAPI.coralium_plague);
			if(player.isPotionActive(AbyssalCraftAPI.dread_plague))
				player.removePotionEffect(AbyssalCraftAPI.dread_plague);
		}
		if(itemstack.getItem() == ACItems.ethaxium_chestplate){
			if(player.isBurning() || world.provider.doesWaterVaporize())
				player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 20, 2, false, false));
			if(player.isPotionActive(AbyssalCraftAPI.antimatter_potion))
				player.removePotionEffect(AbyssalCraftAPI.antimatter_potion);
			if(world.rand.nextInt(200) == 0)
				player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 60, 0, false, false));
		}
		if(itemstack.getItem() == ACItems.ethaxium_leggings)
			if(world.rand.nextInt(200) == 0)
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 60, 0, false, false));
		if(itemstack.getItem() == ACItems.ethaxium_boots)
			if(player.isInWater()){
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20, 2, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 20, 1, false, false));
			} else player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20, 1, false, false));
	}
}
