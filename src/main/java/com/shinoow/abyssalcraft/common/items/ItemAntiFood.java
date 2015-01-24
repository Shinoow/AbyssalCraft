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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemAntiFood extends ItemFood {

	public ItemAntiFood(String par1) {
		super(0, 0, true);
		setUnlocalizedName(par1);
		setTextureName("abyssalcraft:" + par1);
		setCreativeTab(AbyssalCraft.tabFood);
	}

	@Override
	public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		world.playSoundAtEntity(entityPlayer, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);

		if(itemStack.getItem() == AbyssalCraft.antiFlesh)
			entityPlayer.addPotionEffect(new PotionEffect(Potion.field_76443_y.id, 600, 1));
		else if(itemStack.getItem() == AbyssalCraft.antiSpider_eye)
			entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 400, 0));
		else entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 600, 1));
	}
}