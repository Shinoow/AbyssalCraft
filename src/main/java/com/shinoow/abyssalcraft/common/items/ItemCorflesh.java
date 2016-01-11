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
package com.shinoow.abyssalcraft.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

public class ItemCorflesh extends ItemFood {


	public ItemCorflesh(int j, float f, boolean b, String name) {
		super(j, f, b);
		setUnlocalizedName(name);
		setTextureName("abyssalcraft:" + name);
		setCreativeTab(AbyssalCraft.tabFood);
	}

	@Override
	public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		if(itemStack.getItem() == AbyssalCraft.antiCorflesh){
			entityPlayer.addPotionEffect(new PotionEffect(Potion.field_76443_y.id, 600, 1));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600, 0));
		} else {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 600, 1));
			if(!EntityUtil.isPlayerCoralium(entityPlayer))
				entityPlayer.addPotionEffect(new PotionEffect(AbyssalCraft.Cplague.id, 600, 0));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 600, 0));
		}
	}
}
