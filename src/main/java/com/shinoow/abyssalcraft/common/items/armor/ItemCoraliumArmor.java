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
import com.shinoow.abyssalcraft.api.item.ACItems;

public class ItemCoraliumArmor extends ItemArmor {
	public ItemCoraliumArmor(ArmorMaterial par2EnumArmorMaterial, int par3, EntityEquipmentSlot par4, String name){
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
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
	{
		if(stack.getItem() == ACItems.refined_coralium_helmet || stack.getItem() == ACItems.refined_coralium_chestplate || stack.getItem() == ACItems.refined_coralium_boots)
			return "abyssalcraft:textures/armor/coralium_1.png";

		if(stack.getItem() == ACItems.refined_coralium_leggings)
			return "abyssalcraft:textures/armor/coralium_2.png";
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
		if (itemstack.getItem() == ACItems.refined_coralium_helmet)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.waterBreathing, 20, 0));
			player.addPotionEffect(new PotionEffect(MobEffects.nightVision, 260, 0));
		}
		if (itemstack.getItem() == ACItems.refined_coralium_chestplate)
			player.addPotionEffect(new PotionEffect(MobEffects.resistance, 20, 0));
		if (itemstack.getItem() == ACItems.refined_coralium_boots)
			player.addPotionEffect(new PotionEffect(MobEffects.moveSpeed, 20, 1));
	}
}
