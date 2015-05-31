/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
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
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemAntiFood extends ItemFood {

	public ItemAntiFood(String par1, boolean par2) {
		super(0, 0, par2);
		setUnlocalizedName(par1);
		setTextureName("abyssalcraft:" + par1);
		setCreativeTab(AbyssalCraft.tabFood);
	}

	public ItemAntiFood(String par1) {
		this(par1, true);
	}

	@Override
	public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		if(itemStack.getItem() == AbyssalCraft.antiFlesh)
			entityPlayer.addPotionEffect(new PotionEffect(Potion.field_76443_y.id, 600, 1));
		else if(itemStack.getItem() == AbyssalCraft.antiSpider_eye)
			entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 400, 0));
		else entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 600, 1));
	}
}
