/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemCrystalBag extends ItemACBasic {

	public ItemCrystalBag(String par1) {
		super(par1);
		setMaxStackSize(1);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 1;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		setInventorySize(player.getHeldItem(hand));
		if (!world.isRemote)
			if (!player.isSneaking())
				player.openGui(AbyssalCraft.instance, ACLib.crystalbagGuiID, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
		return new ActionResult(EnumActionResult.PASS, player.getHeldItem(hand));
	}

	@Override
	public void addInformation(ItemStack is, World player, List l, ITooltipFlag B)
	{
		l.add(I18n.format("tooltip.crystalbag"));
	}

	public void setInventorySize(ItemStack stack){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(!stack.getTagCompound().hasKey("InvSize")){
			if(stack.getItem() == ACItems.small_crystal_bag)
				stack.getTagCompound().setInteger("InvSize", 18);
			if(stack.getItem() == ACItems.medium_crystal_bag)
				stack.getTagCompound().setInteger("InvSize", 36);
			if(stack.getItem() == ACItems.large_crystal_bag)
				stack.getTagCompound().setInteger("InvSize", 54);
			if(stack.getItem() == ACItems.huge_crystal_bag)
				stack.getTagCompound().setInteger("InvSize", 72);
		}
	}
}
