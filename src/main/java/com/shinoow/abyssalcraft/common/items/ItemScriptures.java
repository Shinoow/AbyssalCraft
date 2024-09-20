/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
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

import com.shinoow.abyssalcraft.api.knowledge.condition.caps.INecroDataCapability;
import com.shinoow.abyssalcraft.api.knowledge.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.NecroDataCapMessage;
import com.shinoow.abyssalcraft.lib.item.ItemACBasic;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemScriptures extends ItemACBasic {

	public ItemScriptures() {
		super("scriptures_omniscience");
		setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);

		if(!world.isRemote){
			INecroDataCapability cap = NecroDataCapability.getCap(player);
			if(!cap.hasUnlockedAllKnowledge()){
				cap.unlockAllKnowledge(true);
				cap.setKnowledgeLevel(3);
				if(!player.capabilities.isCreativeMode)
					stack.shrink(1);
			}

			cap.setLastSyncTime(System.currentTimeMillis());
			PacketDispatcher.sendTo(new NecroDataCapMessage(player), (EntityPlayerMP)player);
		}

		return new ActionResult(EnumActionResult.PASS, stack);
	}

	@Override
	public void addInformation(ItemStack is, World player, List<String> l, ITooltipFlag B){
		l.add("Everything... all in your hands...");
	}
}
