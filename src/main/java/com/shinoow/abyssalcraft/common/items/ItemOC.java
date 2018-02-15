/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
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

public class ItemOC extends ItemACBasic {

	public ItemOC() {
		super("oc");
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return TextFormatting.DARK_RED + super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, EnumHand hand)
	{
		par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.SPEED, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 6000, 6));
		par3EntityPlayer.addPotionEffect(new PotionEffect(MobEffects.HASTE, 6000, 6));

		par1ItemStack.stackSize--;
		return new ActionResult(EnumActionResult.PASS, par1ItemStack);
	}

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
