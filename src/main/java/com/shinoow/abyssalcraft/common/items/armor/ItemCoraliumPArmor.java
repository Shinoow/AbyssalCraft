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
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class ItemCoraliumPArmor extends ItemArmor {
	public ItemCoraliumPArmor(ArmorMaterial par2EnumArmorMaterial, int par3, int par4, String name){
		super(par2EnumArmorMaterial, par3, par4);
		setUnlocalizedName(name);
		setCreativeTab(ACTabs.tabCombat);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.GREEN + super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if(stack.getItem() == ACItems.plated_coralium_helmet || stack.getItem() == ACItems.plated_coralium_chestplate || stack.getItem() == ACItems.plated_coralium_boots)
			return "abyssalcraft:textures/armor/coraliump_1.png";

		if(stack.getItem() == ACItems.plated_coralium_leggings)
			return "abyssalcraft:textures/armor/coraliump_2.png";
		else return null;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		if(world.isRemote) return;
		if (itemstack.getItem() == ACItems.plated_coralium_helmet) {
			player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 260, 0));
			if(player.getActivePotionEffect(AbyssalCraftAPI.coralium_plague) !=null)
				player.removePotionEffect(AbyssalCraftAPI.coralium_plague.getId());
		}
		if (itemstack.getItem() == ACItems.plated_coralium_boots)
			if(player.isInWater()){
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 20, 2));
				player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 20, 1));
			} else player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 20, 1));
	}
}