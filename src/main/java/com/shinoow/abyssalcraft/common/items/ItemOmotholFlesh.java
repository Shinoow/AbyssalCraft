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
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class ItemOmotholFlesh extends ItemFood {

	public ItemOmotholFlesh(int par1, float par2, boolean par3) {
		super(par1, par2, par3);
		setUnlocalizedName("omotholflesh");
		setCreativeTab(ACTabs.tabFood);
	}

	@Override
	public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		world.playSound(entityPlayer, entityPlayer.getPosition(), SoundEvents.entity_player_burp, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		if(EntityUtil.isPlayerCoralium(entityPlayer)){
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.weakness, 100));
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.hunger, 300, 1));
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.confusion, 200));
		} else {
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.weakness, 100));
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.hunger, 400, 1));
			entityPlayer.addPotionEffect(new PotionEffect(MobEffects.confusion, 300));
		}

		entityPlayer.addPotionEffect(new PotionEffect(MobEffects.blindness, 40));
		entityPlayer.addPotionEffect(new PotionEffect(MobEffects.nightVision, 40));
	}
}
