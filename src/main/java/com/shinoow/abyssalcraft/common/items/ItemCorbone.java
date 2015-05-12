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
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

public class ItemCorbone extends ItemFood {



	public ItemCorbone(int j, float f, boolean b, String name) {
		super(j, f, b);
		setUnlocalizedName(name);
		setTextureName("abyssalcraft:" + name);
		setCreativeTab(AbyssalCraft.tabFood);
	}

	@Override
	public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		if(itemStack.getItem() == AbyssalCraft.antiCorbone){
			entityPlayer.addPotionEffect(new PotionEffect(Potion.field_76443_y.id, 600, 1));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600, 0));
			entityPlayer.inventory.addItemStackToInventory(new ItemStack(AbyssalCraft.antiBone));
		} else {
			entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 600, 1));
			if(!EntityUtil.isPlayerCoralium(entityPlayer))
				entityPlayer.addPotionEffect(new PotionEffect(AbyssalCraft.Cplague.id, 600, 0));
			entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 600, 0));
			entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.bone));
		}
	}
}