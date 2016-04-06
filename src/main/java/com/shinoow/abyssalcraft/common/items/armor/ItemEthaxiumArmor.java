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
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;

public class ItemEthaxiumArmor extends ItemArmor {
	public ItemEthaxiumArmor(ArmorMaterial par2EnumArmorMaterial, int par3, EntityEquipmentSlot par4, String name){
		super(par2EnumArmorMaterial, par3, par4);
		//		GameRegistry.registerItem(this, name);
		setUnlocalizedName(name);
		setCreativeTab(AbyssalCraft.tabCombat);
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

	//	@Override
	//	@SideOnly(Side.CLIENT)
	//	public void registerIcons(IIconRegister par1IconRegister)
	//	{
	//		itemIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + this.getUnlocalizedName().substring(5));
	//	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		if (itemstack.getItem() == ACItems.ethaxium_helmet) {
			if(world.provider.isSurfaceWorld() && !world.provider.isDaytime())
				player.addPotionEffect(new PotionEffect(MobEffects.nightVision, 260, 0));
			if(player.getActivePotionEffect(MobEffects.hunger) !=null)
				player.removePotionEffect(MobEffects.hunger);
			if(player.getActivePotionEffect(MobEffects.poison) !=null)
				player.removePotionEffect(MobEffects.poison);
			if(world.rand.nextInt(300) == 0)
				player.addPotionEffect(new PotionEffect(MobEffects.saturation, 60, 0));
		}
		if(itemstack.getItem() == ACItems.ethaxium_chestplate){
			if(player.isBurning() || world.provider.doesWaterVaporize())
				player.addPotionEffect(new PotionEffect(MobEffects.fireResistance, 20, 2));
			if(player.getActivePotionEffect(AbyssalCraftAPI.antimatter_potion) !=null)
				player.removePotionEffect(AbyssalCraftAPI.antimatter_potion);
			if(world.rand.nextInt(200) == 0)
				player.addPotionEffect(new PotionEffect(MobEffects.damageBoost, 60));
		}
		if(itemstack.getItem() == ACItems.ethaxium_leggings)
			if(world.rand.nextInt(200) == 0)
				player.addPotionEffect(new PotionEffect(MobEffects.regeneration, 60));
		if(itemstack.getItem() == ACItems.ethaxium_boots)
			if(player.isInWater()){
				player.addPotionEffect(new PotionEffect(MobEffects.moveSpeed, 20, 2));
				player.addPotionEffect(new PotionEffect(MobEffects.waterBreathing, 20, 1));
			}

		if(player.getActivePotionEffect(MobEffects.saturation) != null && player.getActivePotionEffect(MobEffects.saturation).getDuration() == 0)
			player.removePotionEffect(MobEffects.saturation);
		if(player.getActivePotionEffect(MobEffects.damageBoost) != null && player.getActivePotionEffect(MobEffects.damageBoost).getDuration() == 0)
			player.removePotionEffect(MobEffects.damageBoost);
		if(player.getActivePotionEffect(MobEffects.regeneration) != null && player.getActivePotionEffect(MobEffects.regeneration).getDuration() == 0)
			player.removePotionEffect(MobEffects.regeneration);
		if(player.getActivePotionEffect(MobEffects.fireResistance) != null && player.getActivePotionEffect(MobEffects.fireResistance).getDuration() == 0)
			player.removePotionEffect(MobEffects.fireResistance);
	}
}
