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

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class ItemAbyssalniteCSword extends ItemACSword {

	public ItemAbyssalniteCSword(ToolMaterial mat, String name) {
		super(mat, name, EnumChatFormatting.AQUA);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B)
	{
		l.add(StatCollector.translateToLocal("tooltip.csword.1"));
		l.add(StatCollector.translateToLocal("tooltip.csword.2"));
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
	{
		par1ItemStack.damageItem(10, par3EntityLivingBase);
		par2EntityLivingBase.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 200, 3));
		par2EntityLivingBase.addPotionEffect(new PotionEffect(Potion.wither.id, 100, 1));
		par2EntityLivingBase.addPotionEffect(new PotionEffect(Potion.poison.id, 100, 1));
		par2EntityLivingBase.setFire(4);
		return true;
	}
}