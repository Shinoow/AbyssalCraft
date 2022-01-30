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
import com.shinoow.abyssalcraft.api.necronomicon.condition.DimensionCondition;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.SyncNecromancyDataMessage;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemFaceBook extends ItemACBasic {

	public ItemFaceBook(String par1) {
		super(par1);
		setMaxStackSize(1);
		setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World par2World, EntityPlayer par3EntityPlayer, EnumHand hand)
	{
		ItemStack stack = par3EntityPlayer.getHeldItem(hand);

		if(!par3EntityPlayer.isSneaking()){
			if(!par2World.isRemote)
				PacketDispatcher.sendTo(new SyncNecromancyDataMessage(par2World), (EntityPlayerMP)par3EntityPlayer);
			par3EntityPlayer.openGui(AbyssalCraft.instance, ACLib.faceBookGuiID, par2World, 0, 0, 0);
			return new ActionResult(EnumActionResult.SUCCESS, stack);
		}

		return new ActionResult(EnumActionResult.PASS, stack);
	}

	@Override
	public void addInformation(ItemStack is, World player, List<String> l, ITooltipFlag B){
		l.add(I18n.format("tooltip.face_book.1"));
		l.add(I18n.format("tooltip.face_book.2"));
	}
}
