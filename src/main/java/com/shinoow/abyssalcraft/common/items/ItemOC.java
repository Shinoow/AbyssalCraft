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

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.lib.ACTabs;

public class ItemOC extends Item {

	public ItemOC() {
		setUnlocalizedName("oc");
		setCreativeTab(ACTabs.tabItems);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return TextFormatting.DARK_RED + super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, EnumHand hand)
	{
		par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.damageBoost, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.nightVision, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.invisibility, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.regeneration, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.moveSpeed, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.resistance, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.fireResistance, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.waterBreathing, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.jump, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.digSpeed, 6000, 6));

		par1ItemStack.stackSize--;
		return new ActionResult(EnumActionResult.PASS, par1ItemStack);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		l.add(I18n.translateToLocal("tooltip.oc"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}
}
