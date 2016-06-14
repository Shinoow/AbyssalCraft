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

import java.util.List;

import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemOC extends Item {

	public ItemOC() {
		super();
		setUnlocalizedName("oc");
		setCreativeTab(ACTabs.tabItems);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.DARK_RED + super.getItemStackDisplayName(par1ItemStack);
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
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}
}