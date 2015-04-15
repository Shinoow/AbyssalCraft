/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
		setUnlocalizedName("omotholflesh");
		setTextureName("abyssalcraft:" + "omotholflesh");
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