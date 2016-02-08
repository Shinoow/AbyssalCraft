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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemOmotholFlesh extends ItemFood {

	public ItemOmotholFlesh(int par1, float par2, boolean par3) {
		super(par1, par2, par3);
		//		GameRegistry.registerItem(this, "omotholflesh");
		setUnlocalizedName("omotholflesh");
		//		setTextureName("abyssalcraft:" + "omotholflesh");
		setCreativeTab(AbyssalCraft.tabFood);
	}

	@Override
	public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		world.playSoundAtEntity(entityPlayer, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		if(EntityUtil.isPlayerCoralium(entityPlayer)){
			entityPlayer.addPotionEffect(new PotionEffect(Potion.weakness.id, 100));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 300, 1));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 200));
		} else {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.weakness.id, 100));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 400, 1));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 300));
		}

		entityPlayer.addPotionEffect(new PotionEffect(Potion.blindness.id, 40));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 40));
	}
}
