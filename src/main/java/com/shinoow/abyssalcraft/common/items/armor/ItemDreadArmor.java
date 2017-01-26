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
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class ItemDreadArmor extends ItemArmor {
	public ItemDreadArmor(ArmorMaterial par2EnumArmorMaterial, int par3, int par4, String name){
		super(par2EnumArmorMaterial, par3, par4);
		setUnlocalizedName(name);
		setCreativeTab(ACTabs.tabCombat);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.DARK_RED + super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		if(stack.getItem() == ACItems.dreaded_abyssalnite_helmet || stack.getItem() == ACItems.dreaded_abyssalnite_chestplate || stack.getItem() == ACItems.dreaded_abyssalnite_boots)
			return "abyssalcraft:textures/armor/dread_1.png";

		if(stack.getItem() == ACItems.dreaded_abyssalnite_leggings)
			return "abyssalcraft:textures/armor/dread_2.png";
		else return null;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		if(world.isRemote) return;
		if (itemstack.getItem() == ACItems.dreaded_abyssalnite_helmet)
			player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 260, 0));
		if (itemstack.getItem() == ACItems.dreaded_abyssalnite_chestplate)
			player.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(), 20, 3));
		if (itemstack.getItem() == ACItems.dreaded_abyssalnite_leggings)
			player.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(), 20, 3));
		if (itemstack.getItem() == ACItems.dreaded_abyssalnite_boots)
			player.addPotionEffect(new PotionEffect(Potion.fireResistance.getId(), 20, 3));
	}
}
