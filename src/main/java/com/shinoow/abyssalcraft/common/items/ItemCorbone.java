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
package com.shinoow.abyssalcraft.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class ItemCorbone extends ItemFood {

	public ItemCorbone(int j, float f, boolean b, String name) {
		super(j, f, b);
		setUnlocalizedName(name);
		setCreativeTab(ACTabs.tabFood);
	}

	@Override
	public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		if(itemStack.getItem() == ACItems.anti_plagued_flesh_on_a_bone){
			entityPlayer.addPotionEffect(new PotionEffect(Potion.saturation.id, 600, 1));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600, 0));
			entityPlayer.inventory.addItemStackToInventory(new ItemStack(ACItems.anti_bone));
		} else {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 600, 1));
			if(!EntityUtil.isPlayerCoralium(entityPlayer))
				entityPlayer.addPotionEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague.id, 600, 0));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 600, 0));
			entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.bone));
		}
	}
}
