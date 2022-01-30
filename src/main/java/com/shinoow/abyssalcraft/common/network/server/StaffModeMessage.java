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
package com.shinoow.abyssalcraft.common.network.server;

import java.io.IOException;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.network.AbstractMessage.AbstractServerMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;

public class StaffModeMessage extends AbstractServerMessage<StaffModeMessage> {

	public StaffModeMessage(){}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {

	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {

	}

	@Override
	public void process(EntityPlayer player, Side side) {

		ItemStack mainStack = player.getHeldItem(EnumHand.MAIN_HAND);
		ItemStack offStack = player.getHeldItem(EnumHand.OFF_HAND);
		if(!mainStack.isEmpty() && mainStack.getItem() == ACItems.staff_of_the_gatekeeper){
			if(!mainStack.hasTagCompound())
				mainStack.setTagCompound(new NBTTagCompound());
			int mode = mainStack.getTagCompound().getInteger("Mode");
			if(mode == 0)
				mainStack.getTagCompound().setInteger("Mode", 1);
			else mainStack.getTagCompound().setInteger("Mode", 0);
		}
		if(!offStack.isEmpty() && offStack.getItem() == ACItems.staff_of_the_gatekeeper){
			if(!offStack.hasTagCompound())
				offStack.setTagCompound(new NBTTagCompound());
			int mode = offStack.getTagCompound().getInteger("Mode");
			if(mode == 0)
				offStack.getTagCompound().setInteger("Mode", 1);
			else offStack.getTagCompound().setInteger("Mode", 0);
		}
	}
}
