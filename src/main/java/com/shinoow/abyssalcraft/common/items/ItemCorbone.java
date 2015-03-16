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

	private boolean isAnti;

	public ItemCorbone(int j, float f, boolean b, boolean anti) {
		super(j, f, b);
		isAnti = anti;
	}

	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		super.onEaten(par1ItemStack, par2World, par3EntityPlayer);
		if(!isAnti){
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 600, 1));
			if(!EntityUtil.isPlayerCoralium(par3EntityPlayer))
				par3EntityPlayer.addPotionEffect(new PotionEffect(AbyssalCraft.Cplague.id, 600, 0));
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 600, 0));
			return new ItemStack(Items.bone);
		} else{
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.field_76443_y.id, 600, 1));
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600, 0));
			return new ItemStack(AbyssalCraft.antiBone);
		}

	}
}
