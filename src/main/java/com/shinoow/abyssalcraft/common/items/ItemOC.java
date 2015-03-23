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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemOC extends Item {

	public ItemOC() {
		super();
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.DARK_RED + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.invisibility.id, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.resistance.id, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.jump.id, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 6));

		par1ItemStack.stackSize--;
		return par1ItemStack;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		l.add(StatCollector.translateToLocal("tooltip.oc"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack, int pass)
	{
		return true;
	}
}